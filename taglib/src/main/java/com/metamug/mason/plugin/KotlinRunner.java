package com.metamug.mason.plugin;

import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.exception.MasonError;
import com.metamug.mason.exception.MasonException;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.jsp.JspException;
import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs Kotlin scripts (.kts) from /WEB-INF/scripts/ using the Kotlin JSR-223 engine.
 * The engine jars (kotlin-scripting-jsr223 and dependencies) must be on the server classpath;
 * Mason itself has no compile-time dependency on Kotlin.
 *
 * <pre>
 * &lt;m:execute className="com.metamug.mason.plugin.KotlinRunner"&gt;
 *   &lt;m:arg name="file" value="hash.kts"/&gt;
 * &lt;/m:execute&gt;
 * </pre>
 *
 * Variables available to a script:
 * <ul>
 * <li><code>params</code>: Map&lt;String, String&gt; request parameters</li>
 * <li><code>request</code>: the Mason request (<code>request.id</code>, <code>request.body</code>, <code>request.method</code>)</li>
 * <li><code>response</code>: MutableMap&lt;String, Any?&gt;, whatever is put here is the output of the step</li>
 * <li><code>steps</code>: Map&lt;String, Any?&gt; results of the steps before this one by id (SQL results as a list of row maps,
 * XRequest as its response map, Script/Execute as the response map)</li>
 * <li><code>ds</code>: javax.sql.DataSource of the app</li>
 * </ul>
 *
 * Performance: a script is wrapped into a Kotlin function literal and evaluated <em>once per file version</em>; every request
 * then just invokes that function object. (Evaluating the compiled script on every request through JSR-223 costs about
 * 28 ms because of the engine's REPL machinery.) The body therefore runs as the body of a lambda: no top-level
 * classes/objects (local ones are fine), and no <code>return</code>.
 */
public class KotlinRunner implements RequestProcessable {

    private static final String SCRIPT_ROOT = "/WEB-INF/scripts/";
    private static final String ENGINE_EXTENSION = "kts";
    private static final String FUNCTION_INTERFACE = "kotlin.jvm.functions.Function5";

    /**
     * Opening of the function literal. It is put on the first line together with the imports, so the script body
     * starts on line 2 of the compiled source; {@link #userLines(String)} maps error positions back to the file.
     */
    private static final String FUNCTION_HEAD
            = "val __r2fn: (Map<String, String>, com.metamug.mason.entity.request.MasonRequest, MutableMap<String, Any?>, "
            + "Map<String, Any?>, javax.sql.DataSource) -> Unit = { params, request, response, steps, ds ->";

    private static final Map<String, CachedScript> CACHE = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {
        String file = (String) args.get("file");
        MasonRequest masonRequest = (MasonRequest) request;
        File script = new File(masonRequest.getRealPath(SCRIPT_ROOT), file);
        if (!script.isFile()) {
            throw new JspException("", new MasonException(MasonError.SCRIPT_ERROR, "Script not found: " + file));
        }

        Map<String, Object> output = new LinkedHashMap<>();
        try {
            CachedScript cached = compiled(script);
            cached.invoke.invoke(cached.function, request.getParams(), masonRequest, output, steps(args.get("__steps")), ds);
        } catch (ScriptException ex) {
            // compile errors carry line numbers; the function head does not shift them (see userLines)
            throw scriptError(file, userLines(ex.getMessage()));
        } catch (InvocationTargetException ex) {
            // the script itself threw
            throw scriptError(file, runtimeMessage(ex.getCause() != null ? ex.getCause() : ex));
        }
        return new Response<Object>(output);
    }

    private static JspException scriptError(String file, String detail) {
        String message = file + ": " + detail;
        Logger.getLogger(KotlinRunner.class.getName()).log(Level.SEVERE, message);
        return new JspException("", new MasonException(MasonError.SCRIPT_ERROR, message));
    }

    /**
     * Results of the steps before this script by step id. SQL query results are converted to a plain
     * List of column-name to value maps; XRequest/Script results are already maps and lists.
     */
    private static Map<String, Object> steps(Object raw) {
        Map<String, Object> steps = new LinkedHashMap<>();
        if (raw instanceof Map) {
            for (Map.Entry<?, ?> e : ((Map<?, ?>) raw).entrySet()) {
                Object value = e.getValue();
                if (value instanceof javax.servlet.jsp.jstl.sql.Result) {
                    List<Map<String, Object>> rows = new ArrayList<>();
                    for (Object row : ((javax.servlet.jsp.jstl.sql.Result) value).getRows()) {
                        rows.add(new LinkedHashMap<String, Object>((Map<String, Object>) row));
                    }
                    value = rows;
                }
                steps.put(String.valueOf(e.getKey()), value);
            }
        }
        return steps;
    }

    /**
     * The Kotlin JSR-223 engine is REPL style: everything compiled in one engine instance is kept as history,
     * so re-declaring a name in a later (updated) script clashes. Every script version therefore gets its own engine.
     */
    private static ScriptEngine newEngine() throws JspException {
        ScriptEngine e = new ScriptEngineManager(KotlinRunner.class.getClassLoader())
                .getEngineByExtension(ENGINE_EXTENSION);
        if (e == null) {
            throw new JspException("", new MasonException(MasonError.SCRIPT_ERROR,
                    "Kotlin script engine not found. Add kotlin-scripting-jsr223 and its dependencies to the server classpath."));
        }
        return e;
    }

    private static CachedScript compiled(File script) throws Exception {
        String key = script.getCanonicalPath();
        long modified = script.lastModified();
        CachedScript cached = CACHE.get(key);
        if (cached != null && cached.modified == modified) {
            return cached;
        }
        // compile once per file version; concurrent first requests wait instead of compiling twice
        synchronized (KotlinRunner.class) {
            cached = CACHE.get(key);
            if (cached != null && cached.modified == modified) {
                return cached;
            }
            ScriptEngine kotlin = newEngine();
            String source = new String(Files.readAllBytes(script.toPath()), StandardCharsets.UTF_8);
            CompiledScript compiled = ((Compilable) kotlin).compile(wrap(source));
            // evaluating the wrapped source yields the function object (its last expression)
            Object function = compiled.eval(kotlin.createBindings());
            if (function == null) {
                throw new ScriptException("script did not produce a function");
            }
            Class<?> iface = Class.forName(FUNCTION_INTERFACE, true, function.getClass().getClassLoader());
            Method invoke = iface.getMethod("invoke", Object.class, Object.class, Object.class, Object.class, Object.class);
            CachedScript created = new CachedScript(modified, function, invoke);
            CACHE.put(key, created);
            return created;
        }
    }

    /**
     * Moves the script's import lines to the first line (imports must precede declarations), opens the function
     * literal on that same line and closes it after the body. The body starts on line 2 of the compiled source.
     */
    static String wrap(String source) {
        List<String> imports = new ArrayList<>();
        StringBuilder body = new StringBuilder();
        String[] lines = source.split("\\R", -1);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().startsWith("import ")) {
                imports.add(line.trim().replaceAll(";$", ""));
                line = "";
            }
            if (i > 0) {
                body.append('\n');
            }
            body.append(line);
        }
        StringBuilder first = new StringBuilder();
        for (String imp : imports) {
            first.append(imp).append("; ");
        }
        return first + FUNCTION_HEAD + "\n" + body + "\n}\n__r2fn";
    }

    /**
     * Compile errors read "(ScriptingHostXXXX_Line_0.kts:LINE:COL)" where LINE counts the first (head) line.
     * Rewrites them to "(line LINE:COL)" with LINE relative to the script file.
     */
    static String userLines(String message) {
        if (message == null) {
            return "";
        }
        Matcher m = Pattern.compile("\\(ScriptingHost\\w+_Line_\\d+\\.kts:(\\d+):(\\d+)\\)").matcher(message);
        StringBuffer out = new StringBuffer();
        while (m.find()) {
            int line = Math.max(1, Integer.parseInt(m.group(1)) - 1);
            m.appendReplacement(out, Matcher.quoteReplacement("(line " + line + ":" + m.group(2) + ")"));
        }
        m.appendTail(out);
        return out.toString();
    }

    /**
     * Message for an exception thrown by the script while running: the exception and the script line it came from.
     */
    static String runtimeMessage(Throwable t) {
        String where = "";
        for (StackTraceElement e : t.getStackTrace()) {
            if (e.getFileName() != null && e.getFileName().endsWith(".kts")) {
                where = " (line " + Math.max(1, e.getLineNumber() - 1) + ")";
                break;
            }
        }
        return "runtime error: " + t + where;
    }

    private static final class CachedScript {

        final long modified;
        final Object function;
        final Method invoke;

        CachedScript(long modified, Object function, Method invoke) {
            this.modified = modified;
            this.function = function;
            this.invoke = invoke;
        }
    }
}

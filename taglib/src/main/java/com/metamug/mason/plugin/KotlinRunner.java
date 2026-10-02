package com.metamug.mason.plugin;

import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.exception.MasonError;
import com.metamug.mason.exception.MasonException;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.jsp.JspException;
import javax.sql.DataSource;
import java.io.File;
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
 * <li><code>ds</code>: javax.sql.DataSource of the app</li>
 * </ul>
 *
 * Compiled scripts are cached until the file changes, so only the first call pays the compile cost.
 */
public class KotlinRunner implements RequestProcessable {

    private static final String SCRIPT_ROOT = "/WEB-INF/scripts/";
    private static final String ENGINE_EXTENSION = "kts";

    /**
     * The typed names are declared on a single line so that line numbers in compile errors
     * match the script file. The bindings use private names because the engine also exposes every
     * binding as an untyped top level variable, which would clash with the typed declarations.
     */
    private static final String PRELUDE_DECLARATIONS
            = "@Suppress(\"UNCHECKED_CAST\") val params: Map<String, String> = bindings[\"__params\"] as Map<String, String>; "
            + "@Suppress(\"UNCHECKED_CAST\") val response: MutableMap<String, Any?> = bindings[\"__response\"] as MutableMap<String, Any?>; "
            + "val request: com.metamug.mason.entity.request.MasonRequest = bindings[\"__request\"] as com.metamug.mason.entity.request.MasonRequest; "
            + "val ds: javax.sql.DataSource = bindings[\"__ds\"] as javax.sql.DataSource";

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
            CompiledScript compiled = cached.compiled;
            Bindings bindings = cached.engine.createBindings();
            bindings.put("__params", request.getParams());
            bindings.put("__response", output);
            bindings.put("__request", masonRequest);
            bindings.put("__ds", ds);
            compiled.eval(bindings);
        } catch (ScriptException ex) {
            // compile and runtime errors carry file line numbers (the prelude does not shift them)
            String message = file + ": " + userLines(ex.getMessage());
            Logger.getLogger(KotlinRunner.class.getName()).log(Level.SEVERE, message);
            throw new JspException("", new MasonException(MasonError.SCRIPT_ERROR, message));
        }
        return new Response<Object>(output);
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
            CachedScript created = new CachedScript(modified, kotlin, compiled);
            CACHE.put(key, created);
            return created;
        }
    }

    /**
     * Moves the script's import lines to the first line (imports must precede declarations) and adds the
     * typed declarations on that same line. The script body therefore starts on line 2 of the compiled
     * source; {@link #userLines(String)} maps error positions back to the script file.
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
        return first + PRELUDE_DECLARATIONS + "\n" + body;
    }

    /**
     * Compile errors read "(ScriptingHostXXXX_Line_0.kts:LINE:COL)" where LINE counts the prelude line.
     * Rewrites them to "(file:LINE:COL)" with LINE relative to the script file.
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

    private static final class CachedScript {

        final long modified;
        final ScriptEngine engine;
        final CompiledScript compiled;

        CachedScript(long modified, ScriptEngine engine, CompiledScript compiled) {
            this.modified = modified;
            this.engine = engine;
            this.compiled = compiled;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import com.metamug.api.common.MtgRequest;
import com.metamug.exec.RequestProcessable;
import com.metamug.exec.ResultProcessable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kainix
 */
public class CodeTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String className;
    private Object param;
    private List<Object> parameters;
    @Resource(name = "jdbc/mtgMySQL")
    private DataSource ds;

    public CodeTagHandler() {
        super();
        init();
    }

    private void init() {
        className = null;
        param = null;
        parameters = null;
    }

    @Override
    public int doEndTag() throws JspException {
        JSONObject obj = new JSONObject();
        JspWriter out = null;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        try {
            out = pageContext.getOut();
            Object result = null;
            Class cls = Class.forName((String) className);
            Object newInstance = cls.newInstance();
            ResultProcessable resProcessable;
            RequestProcessable reqProcessable;
            if (ResultProcessable.class.isAssignableFrom(cls)) {
                resProcessable = (ResultProcessable) newInstance;
                try {
                    if (param instanceof ResultImpl) {
                        ResultImpl ri = (ResultImpl) param;
                        result = resProcessable.process(ri.getRows(), ri.getColumnNames(), ri.getRowCount());
                        obj.put("result", result);
                        out.print(obj);
                        pageContext.setAttribute("Content-Length", ((String) result).length(), PageContext.REQUEST_SCOPE);
                    }
                } catch (IOException ex) {
                    if (ex.getCause() != null) {
                        String cause = ex.getCause().toString();
                        obj.put("message", cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                    } else {
                        obj.put("message", ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                    }
                    obj.put("status", 422);
                    response.setStatus(422);
                    out.print(obj);
                }
            } else if (RequestProcessable.class.isAssignableFrom(cls)) {
                reqProcessable = (RequestProcessable) newInstance;
                try {
                    if (param instanceof MtgRequest) {
                        MtgRequest mtg = (MtgRequest) param;
                        Enumeration<String> headerNames = request.getHeaderNames();
                        Map<String, String> requestHeaders = new HashMap<>();
                        while (headerNames.hasMoreElements()) {
                            String header = headerNames.nextElement();
                            requestHeaders.put(header, request.getHeader(header));
                        }
                        result = reqProcessable.process(mtg.getParams(), ds, requestHeaders);
                        obj.put("result", result);
                        out.print(obj);
                        pageContext.setAttribute("Content-Length", ((String) result).length(), PageContext.REQUEST_SCOPE);
                    }
                } catch (IOException | JSONException ex) {
                    if (ex.getCause() != null) {
                        String cause = ex.getCause().toString();
                        obj.put("message", cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                    } else {
                        obj.put("message", ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                    }
                    obj.put("status", 422);
                    response.setStatus(422);
                }
            } else {
                obj.put("message", "Class isn't processable");
                obj.put("status", 422);
                response.setStatus(422);
                out.print(obj);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | IOException ex) {
            if (ex.getClass().toString().contains("AccessControlException")) {
                obj.put("message", "Access denied, can't access system information.");
                obj.put("status", 403);
                response.setStatus(403);
            } else {
                if (ex.getCause() != null) {
                    String cause = ex.getCause().toString();
                    obj.put("message", cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                } else {
                    obj.put("message", ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                }
                obj.put("status", 422);
                response.setStatus(422);
            }
            try {
                out.print(obj);
            } catch (IOException ex1) {
                Logger.getLogger(CodeTagHandler.class.getName()).log(Level.SEVERE, ex1.getMessage());
            }
        }
        return EVAL_PAGE;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setParam(Object Parameter) {
        this.param = Parameter;
    }

    /**
     * Just rethrows the Throwable.
     *
     * @param throwable
     * @throws java.lang.Throwable
     */
    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    /**
     * Close the <code>Connection</code>, unless this action is used as part of a transaction.
     */
    @Override
    public void doFinally() {
        className = null;
        param = null;
        parameters = null;
        ds = null;
    }

    public void addParameter(Object obj) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.add(obj);
    }
}

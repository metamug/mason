/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author Kaisteel
 */
public class ExceptionTagHandler extends BodyTagSupport implements TryCatchFinally {

    private Object value;

    /**
     * Creates new instance of tag handler
     */
    public ExceptionTagHandler() {
        super();
    }

    /**
     * This method is called after the JSP engine finished processing the tag.
     *
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE. This method is automatically generated. Do not modify this method. Instead, modify the
     * methods that this method calls.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        Exception ex = (Exception) value;
        JspWriter out = pageContext.getOut();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String header = request.getHeader("Accept");
        try {
            System.out.println("Exception:" + ex);
//            ex.printStackTrace();
            if (ex.getCause() != null) {
                String cause = ex.getCause().toString();
                System.out.println("Cause:" + cause);
                if (cause.contains("InputValidationException")) {
                    response.setStatus(412);
                    out.println("{\"message\": \"" + ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " ") + "\",\"status\":" + 412 + "}");
                } else if (cause.contains("MySQLSyntaxErrorException") || cause.contains("MySQLIntegrityConstraintViolationException") || cause.contains("SQLException")) {
                    response.setStatus(500);
                    out.println("{\"message\": \"Incorrect query or constraint violation\",\"status\":" + 500 + "}");
//                    out.println("{\"message\": \"" + cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " ") + "\",\"status\":" + 422 + "}");
                } else if (cause.contains("NumberFormatException") || cause.contains("ParseException")) {
                    response.setStatus(422);
                    out.println("{\"message\": \"Unable to parse input\",\"status\":" + 422 + "}");
                } else if (cause.contains("ResourceNotFoundException")) {
                    response.setStatus(404);
                    out.println("{\"message\": \"Parent resouce not found\",\"status\":" + 404 + "}");
                } else if (cause.contains("InvalidStatusException")) {
                    response.setStatus(406);
                    out.println("{\"message\": \"Invalid Status code set\",\"status\":" + 406 + "}");
                } else if (cause.contains("RoleAuthorizationException")) {
                    response.setStatus(401);
                    response.setHeader("WWW-Authenticate", "Basic");
                    out.println("{\"message\": \"Access Denied to resource due to unauthorization\",\"status\":" + 401 + "}");
                } else if (cause.contains("RoleAccessDeniedException")) {
                    response.setStatus(403);
                    out.println("{\"message\": \"Forbidden Access to resource\",\"status\":" + 403 + "}");
                } else {
                    response.setStatus(409);
                    out.println("{\"message\": \"Conflict in resource file\",\"status\":" + 409 + "}");
//                    out.println("{\"message\": \"" + ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " ") + "\",\"status\":" + 422 + "}");
                }
            } else {
                response.setStatus(500);
                out.println("{\"message\": \"Server Error\",\"status\":" + 500 + "}");
//                out.println("{\"message\": \"" + ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " ") + "\",\"status\":" + 409 + "}");
            }
            Logger.getLogger(ExceptionTagHandler.class.getName()).log(Level.SEVERE, "ExceptionTaglib:{0}", ex.getMessage());
        } catch (IOException ex1) {
            Logger.getLogger(ExceptionTagHandler.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
        }
        return SKIP_PAGE;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        value = null;
    }
}

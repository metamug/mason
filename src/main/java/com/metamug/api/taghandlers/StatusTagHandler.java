/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author Kaisteel
 */
public class StatusTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String value;
    private String message;

    /**
     * Creates new instance of tag handler
     */
    public StatusTagHandler() {
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
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        JspWriter out = pageContext.getOut();
        try {
            response.setStatus(Integer.parseInt(value));
            if (message != null) {
                out.println("{\"message\":\"" + message + "\",\"status\":" + value + "}");
            }
        } catch (NumberFormatException ex) {
            throw new JspException("Invalid status code", new InvalidStatusException(""));
        } catch (IOException ex) {
            Logger.getLogger(StatusTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return EVAL_PAGE;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        value = message = null;
    }

    private static class InvalidStatusException extends Exception {

        public InvalidStatusException(String message) {
            super(message);
        }
    }
}

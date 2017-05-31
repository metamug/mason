/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author Kaisteel
 */
public class HeaderTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String value;

    /**
     * Creates new instance of tag handler
     */
    public HeaderTagHandler() {
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
        try {
            HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
            if (value != null && java.util.Arrays.asList(value.split("/")).contains("xml")) {
                response.setContentType("application/xml;charset=UTF-8");
            } else {
                response.setContentType("application/json;charset=UTF-8");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return EVAL_PAGE;
    }

    public void setValue(String value) {
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

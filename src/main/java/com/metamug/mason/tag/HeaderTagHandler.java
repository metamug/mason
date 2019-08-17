/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import javax.servlet.jsp.JspException;

/**
 *
 * @author anishhirlekar
 */
public class HeaderTagHandler extends RestTag {
    
    private String name;
    private String value;
    
    /**
     * This method is called after the JSP engine finished processing the tag.
     *
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        response.setHeader(name, value);
        
        return EVAL_PAGE;
    }
    
    public void setName(String n) {
        name = n;
    }

    public void setValue(String v) {
        value = v;
    }
}
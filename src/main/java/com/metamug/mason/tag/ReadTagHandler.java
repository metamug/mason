/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.service.ReadService;
import javax.servlet.jsp.JspException;

/**
 *
 * @author anishhirlekar
 */
public class ReadTagHandler extends RestTag {
    private Object source;
    private String mpath;
    private String var;
    
    @Override
    public int doEndTag() throws JspException {
        ReadService readService = new ReadService();
        
        Object value = readService.read(source, mpath);
        
        pageContext.setAttribute(var, value);
        
        return EVAL_PAGE;
    }
    
    public void setSource(Object source) {
        this.source = source;
    }

    public void setMpath(String mpath) {
        this.mpath = mpath;
    }

    public void setVar(String var) {
        this.var = var;
    }
}

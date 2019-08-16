/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.io.mpath.MPathUtil;
import com.metamug.mason.service.ResolveService;
import javax.servlet.jsp.JspException;

/**
 *
 * @author anishhirlekar
 */
public class ResolveTagHandler extends RestTag {
    private String path;
    private String var;
    
    @Override
    public int doEndTag() throws JspException {
        
        String objectName = MPathUtil.getObjectNameFromMPath(path);
        
        Object responseObject = pageContext.getAttribute(objectName);
        
        ResolveService readService = new ResolveService();
        
        String value = readService.read(responseObject, path);
        
        pageContext.setAttribute(var, value);
        
        return EVAL_PAGE;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVar(String var) {
        this.var = var;
    }
}

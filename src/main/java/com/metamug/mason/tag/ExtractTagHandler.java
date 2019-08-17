/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.io.mpath.MPathUtil;
import com.metamug.mason.service.ExtractService;
import javax.servlet.jsp.JspException;

/**
 *
 * @author anishhirlekar
 */
public class ExtractTagHandler extends RestTag {
    private String path;
    
    @Override
    public int doEndTag() throws JspException {
        
        String objectName = MPathUtil.getObjectNameFromMPath(path);
        
        Object responseObject = pageContext.getAttribute(objectName);
        
        ExtractService readService = new ExtractService();
        
        String value = readService.extract(responseObject, path);
        
        pageContext.setAttribute(path.replace("$", ""), value);
        
        return EVAL_PAGE;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

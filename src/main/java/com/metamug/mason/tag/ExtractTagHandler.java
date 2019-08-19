/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.io.mpath.MPathUtil;
import com.metamug.mason.service.ExtractService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author anishhirlekar
 */
public class ExtractTagHandler extends RestTag {
    private String path;
    
    @Override
    public int doEndTag() throws JspException {
        //get var name from path notation
        String var = MPathUtil.getVarName(path);
        //get target result object from bus
        Object target = getFromBus(var);
        
        ExtractService extractService = new ExtractService();
        
        String value = extractService.extract(target, path);
        
        Map<String, Object> extracted = (HashMap)pageContext.getAttribute(EXTRACTED,PageContext.PAGE_SCOPE);
        extracted.put(path.replace("$", ""), value);
        
        return EVAL_PAGE;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

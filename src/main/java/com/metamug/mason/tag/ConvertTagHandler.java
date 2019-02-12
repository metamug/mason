/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.service.ConvertService;
import java.util.LinkedHashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

/**
 *
 * @author anishhirlekar
 */
public class ConvertTagHandler extends BodyTagSupport implements TryCatchFinally {
    private Object result;
    private Object target;
    private String property;
    
    @Override
    public int doEndTag() throws JspException {
        LinkedHashMap<String, Object> targetMap = (LinkedHashMap<String, Object>) target;
        ResultImpl resultSet = (ResultImpl)result;
        
        ConvertService cs = new ConvertService();
        cs.convertResultToMap(resultSet, targetMap, property);
        
        return EVAL_PAGE;
    }
    
    public void setProperty(String p){
        property = p;
    }
    
    public void setResult(Object r){
        result = r;
    }
    
    public void setTarget(Object t){
        target = t;
    }

    @Override
    public void doCatch(Throwable thrwbl) throws Throwable {
        throw thrwbl;
    }

    @Override
    public void doFinally() {
    }
}

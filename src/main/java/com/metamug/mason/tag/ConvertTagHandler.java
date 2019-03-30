/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.service.ConvertService;
import java.util.LinkedHashMap;
import javax.servlet.jsp.JspException;

/**
 *
 * @author anishhirlekar
 */
public class ConvertTagHandler extends RestTag {

    private Object value;
    private Object target;
    private String property;

    @Override
    public int doEndTag() throws JspException {
        LinkedHashMap<String, Object> targetMap = (LinkedHashMap<String, Object>) target;

        ConvertService cs = new ConvertService();

        cs.convertToMap(value, targetMap, property);

        return EVAL_PAGE;
    }

    public void setProperty(String p) {
        property = p;
    }

    public void setValue(Object v) {
        value = v;
    }

    public void setTarget(Object t) {
        target = t;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.entity.Request;
import static com.metamug.mason.Router.MASON_REQUEST;
import com.metamug.mason.entity.ContextMap;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.exception.MasonError;
import com.metamug.mason.exception.MasonException;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import javax.servlet.jsp.JspException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pc
 */
public class ScriptTagHandler extends RestTag {

    private String file, var;
    private static final String SCRIPT_VARIABLE_PREFIX = "_$";

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @param var
     */
    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        runScript();
        return SKIP_BODY;
    }

    public void runScript() throws JspException {
        try {
            //file:/C:/tomcat9/webapps/mason-sample/WEB-INF/classes//WEB_INF/scripts/test.groovy
            GroovyScriptEngine engine = new GroovyScriptEngine(
                    new URL[]{ScriptTagHandler.class.getClassLoader().getResource("..")
                    });
            Binding binding = new Binding();
            
            for (Map.Entry<String, String[]> requestVariable : request.getParameterMap().entrySet()) {
                binding.setVariable("_$" + requestVariable.getKey(), requestVariable.getValue()[0]);
            }

            Map contextMap = new ContextMap(pageContext);
            binding.setVariable(SCRIPT_VARIABLE_PREFIX, contextMap);
            Map<String, Object> object = new LinkedHashMap<>();

            binding.setVariable("response", object); //for the output

            engine.run(SCRIPT_ROOT + file, binding);
            //output to bus
            addToBus(var, object);

        } catch (SecurityException | ResourceException | ScriptException | IllegalArgumentException ex) {
            Logger.getLogger(ScriptTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new JspException("", new MasonException(MasonError.SCRIPT_ERROR));
        }
    }

    private static final String SCRIPT_ROOT = "scripts/";

}

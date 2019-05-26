/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.entity.request.MasonRequest;
import static com.metamug.mason.tag.RestTag.MASON_OUTPUT;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import static javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE;
import static javax.servlet.jsp.tagext.Tag.SKIP_PAGE;

/**
 *
 * @author pc
 */
public class ScriptTagHandler extends RestTag {

    private String scriptPath;

    public void setScriptPath(String file) {
        this.scriptPath = file;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        runScript();
        return SKIP_BODY;
    }

    public void runScript() {
        try {
            GroovyScriptEngine engine = new GroovyScriptEngine(".");
            Binding binding = new Binding();
            MasonRequest masonReq = (MasonRequest) request.getAttribute("mtgReq");
            binding.setVariable("request", masonReq);
            LinkedHashMap<String, Object> masonOutput = (LinkedHashMap<String, Object>) request.getAttribute(MASON_OUTPUT);
            binding.setVariable("response", masonOutput);
            engine.run(SCRIPT_ROOT + scriptPath, binding);
        } catch (IOException | SecurityException | ResourceException | ScriptException | IllegalArgumentException ex) {
            Logger.getLogger(ExecuteTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    private static final String SCRIPT_ROOT = "/WEB_INF/scripts/";

}

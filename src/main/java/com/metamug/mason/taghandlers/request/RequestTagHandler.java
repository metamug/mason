/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.taghandlers.request;

import com.metamug.mason.Router;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.entity.response.DatasetOutput;
import com.metamug.mason.entity.response.JSONOutput;
import com.metamug.mason.entity.response.MasonOutput;
import static com.metamug.mason.entity.response.MasonOutput.HEADER_JSON;
import com.metamug.mason.entity.response.XMLOutput;
import com.metamug.mason.taghandlers.OutputTagHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author anishhirlekar
 */
public class RequestTagHandler extends BodyTagSupport implements TryCatchFinally {
    
    private String method;
    private boolean item;
    private boolean processOutput;

    public RequestTagHandler(){
        super();
        method = null;
        item = false;
        processOutput = false;
    }
    
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        boolean isRequestHandled = (boolean)request.getAttribute(Router.REQUEST_HANDLED);
        if(!isRequestHandled) {
            MasonRequest masonReq = (MasonRequest) pageContext.getRequest().getAttribute("mtgReq");
            if(method.equalsIgnoreCase(request.getMethod())) {
                boolean hasId = masonReq.getId() != null;
                if(hasId == item) {
                    processOutput = true;
                    return EVAL_BODY_INCLUDE;                 
                }
            }
        } 
        processOutput = false;
        return SKIP_BODY;
    }
    
    @Override
    public int doEndTag() throws JspException {
        if(processOutput)
            processOutput();
        return EVAL_PAGE;
    }
    
    public void processOutput() {
        JspWriter out = pageContext.getOut();
        LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) pageContext.getAttribute("map", PageContext.REQUEST_SCOPE);
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        
        if (resultMap.isEmpty()) {
            response.setStatus(204);
            return;
        }

        String header = request.getHeader("Accept") == null ? HEADER_JSON : request.getHeader("Accept");
        MasonOutput output;
        if (Arrays.asList(header.split("/")).contains("xml")) { //Accept: application/xml, text/xml
            output = new XMLOutput(resultMap);
        } else if (Arrays.asList(header.split("/")).contains("json+dataset")) { //Accept: application/json+dataset
            output = new DatasetOutput(resultMap);
        } else { //Accept: application/json OR default
            output = new JSONOutput(resultMap);
        }
        
        String strOutput = output.toString();
        response.setContentType(output.getContentType());
        pageContext.setAttribute("Content-Length", strOutput.length(), PageContext.REQUEST_SCOPE);
        try {
            out.print(strOutput);
        } catch (IOException ex) {
            Logger.getLogger(OutputTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute(Router.REQUEST_HANDLED, Boolean.TRUE);
    }
    
    public void setMethod(String m) {
        method = m;
    }
    
    public void setItem(boolean i){
        item = i;
    }
    
    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    } 
}
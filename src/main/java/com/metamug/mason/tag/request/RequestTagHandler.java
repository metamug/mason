/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag.request;

import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.entity.response.DatasetOutput;
import com.metamug.mason.entity.response.JSONOutput;
import com.metamug.mason.entity.response.MasonOutput;
import static com.metamug.mason.entity.response.MasonOutput.HEADER_JSON;
import com.metamug.mason.entity.response.XMLOutput;
import com.metamug.mason.tag.ResourceTagHandler;
import static com.metamug.mason.tag.ResourceTagHandler.HEADER_ACCEPT;
import static com.metamug.mason.tag.ResourceTagHandler.MASON_OUTPUT;
import com.metamug.mason.tag.RestTag;
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

/**
 *
 * @author anishhirlekar
 */
public class RequestTagHandler extends RestTag {
    
    private String method;
    private boolean item;
    private boolean processOutput;
 
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        MasonRequest masonReq = (MasonRequest) request.getAttribute("mtgReq");
        String reqMethod = (String)request.getAttribute("mtgMethod");
        
        if(method.equalsIgnoreCase(reqMethod)) {
            processOutput = (masonReq.getId() != null) == item;
            if(processOutput) {
                pageContext.setAttribute(MASON_OUTPUT, new LinkedHashMap<>(), PageContext.REQUEST_SCOPE);
                return EVAL_BODY_INCLUDE;                 
            }
        }
        
        return SKIP_BODY;
    }   
    
    @Override
    public int doEndTag() throws JspException {
        if(processOutput) {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
            processOutput(request,response,pageContext.getOut());
            return SKIP_PAGE;
        } else {
            return EVAL_PAGE;
        }
    }
    
    private void processOutput(HttpServletRequest request, HttpServletResponse response, JspWriter out) {
        LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) 
                                        pageContext.getAttribute(MASON_OUTPUT, PageContext.REQUEST_SCOPE); 
        if (resultMap.isEmpty()) {
            response.setStatus(204);
            return;
        }

        String header = request.getHeader(HEADER_ACCEPT) == null ? HEADER_JSON : request.getHeader(HEADER_ACCEPT);
        MasonOutput output;
        if (Arrays.asList(header.split("/")).contains("xml")) { //Accept: application/xml, text/xml
            output = new XMLOutput(resultMap);
        } else if (Arrays.asList(header.split("/")).contains("json+dataset")) { //Accept: application/json+dataset
            output = new DatasetOutput(resultMap);
        } else { //Accept: application/json OR default
            output = new JSONOutput(resultMap);
        }
        
        String op = output.toString();
        response.setContentType(output.getContentType());
        pageContext.setAttribute("Content-Length", op.length(), PageContext.REQUEST_SCOPE);
        try {
            out.print(op);
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
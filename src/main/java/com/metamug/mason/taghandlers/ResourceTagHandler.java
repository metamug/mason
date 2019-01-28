/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.taghandlers;

import com.metamug.mason.Router;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.entity.response.DatasetOutput;
import com.metamug.mason.entity.response.JSONOutput;
import com.metamug.mason.entity.response.MasonOutput;
import com.metamug.mason.entity.response.XMLOutput;
import com.metamug.mason.exceptions.MetamugError;
import com.metamug.mason.exceptions.MetamugException;
import com.metamug.mason.services.AuthService;
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
public class ResourceTagHandler extends BodyTagSupport implements TryCatchFinally{
    private String auth;
    private final transient AuthService authService;
    
    public static final int STATUS_RES_NOT_FOUND = 404;
    public static final String MSG_RES_NOT_FOUND = "Resource not found!";
    
    public static final String HEADER_JSON = "application/json";
    
    public ResourceTagHandler(){
        super();
        auth = null;
        authService = new AuthService();
    }
    
    public void setAuth(String a){
        auth = a;
    }
    
    @Override
    public int doStartTag() throws JspException {
        if(auth != null) {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            processAuth(request);    
        }
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        boolean isRequestHandled = (boolean)request.getAttribute(Router.REQUEST_HANDLED);
        if(isRequestHandled) {
            processOutput(request, response, pageContext.getOut());
        } else{
            process404(request, response, pageContext.getOut());
        }
        return EVAL_PAGE;
    }
    
    public void process404(HttpServletRequest request, HttpServletResponse response, JspWriter out){
        String header = request.getHeader("Accept") == null ? HEADER_JSON : request.getHeader("Accept");
        response.setContentType(header);
        response.setStatus(STATUS_RES_NOT_FOUND);
        try {
            if (Arrays.asList(header.split("/")).contains("xml")) {
                StringBuilder xmlBuilder = new StringBuilder();
                xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                xmlBuilder.append("<response>");
                xmlBuilder.append("\n\t<status>");
                xmlBuilder.append(STATUS_RES_NOT_FOUND);
                xmlBuilder.append("</status>");
                xmlBuilder.append("\n\t<message>");
                xmlBuilder.append(MSG_RES_NOT_FOUND);
                xmlBuilder.append("</message>");
                xmlBuilder.append("\n</response>");
                out.print(xmlBuilder.toString());                       
            } else { 
                out.print("{\"message\":\"" + MSG_RES_NOT_FOUND + "\",\"status\":" + STATUS_RES_NOT_FOUND + "}");
            }
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void processAuth(HttpServletRequest request) throws JspException {
        String header = request.getHeader("Authorization");
        MasonRequest masonReq = (MasonRequest) request.getAttribute("mtgReq");
        try {
            if (header == null) {
                throw new JspException("Access Denied due to unauthorization.",
                        new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
            }
            if (header.contains("Basic ")) {
                masonReq.setUid(validateBasic(header));
            } else if (header.contains("Bearer ")) {
                String bearerToken = header.replaceFirst("Bearer ", "");
                //check jwt format
                //validateJwt - check aud against val, exp
                masonReq.setUid(validateBearer(bearerToken.trim()));
            } else {
                throw new JspException("Access Denied due to unauthorization.", 
                        new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
            }
        } catch (IllegalArgumentException ex) {
            throw new JspException("Access Denied due to unauthorization.", 
                    new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
        }
    }
    
    public void processOutput(HttpServletRequest request, HttpServletResponse response, JspWriter out) {
        LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) 
                                                    pageContext.getAttribute("map", PageContext.REQUEST_SCOPE); 
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
        
        String op = output.toString();
        response.setContentType(output.getContentType());
        pageContext.setAttribute("Content-Length", op.length(), PageContext.REQUEST_SCOPE);
        try {
            out.print(op);
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    } 
    
    private String validateBasic(String header) throws JspException {
        return authService.validateBasic(header, auth);
    }

    private String validateBearer(String bearerToken) throws JspException {
        return authService.validateBearer(bearerToken, auth);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.entity.request.MasonRequest;
import static com.metamug.mason.entity.response.MasonOutput.HEADER_JSON;
import com.metamug.mason.exception.MetamugError;
import com.metamug.mason.exception.MetamugException;
import com.metamug.mason.service.AuthService;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author anishhirlekar
 */
public class ResourceTagHandler extends RestTag {

    private String auth;
    
    @Resource
    private transient AuthService authService;

    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final String MSG_METHOD_NOT_ALLOWED = "Method not allowed";
    public static final String ACCESS_DENIED = "Access Denied due to unauthorization";
    public static final String ACCESS_FORBIDDEN = "Access Denied due to unauthorization!";
    public static final String BEARER_ = "Bearer ";
    
     @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
  
    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        request = (HttpServletRequest) context.getRequest();
        response = (HttpServletResponse) context.getResponse();
        if (StringUtils.isNotBlank(auth)) {
            processAuth();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        process405();
        return SKIP_PAGE;
    }

    private void process405() {
        
        String header = request.getHeader(HEADER_ACCEPT) == null ? HEADER_JSON : request.getHeader(HEADER_ACCEPT);
        response.setContentType(header);
        response.setStatus(STATUS_METHOD_NOT_ALLOWED);
        try {
            if (Arrays.asList(header.split("/")).contains("xml")) {
                StringBuilder xmlBuilder = new StringBuilder();
                xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                xmlBuilder.append("<response>");
                xmlBuilder.append("\n\t<status>");
                xmlBuilder.append(STATUS_METHOD_NOT_ALLOWED);
                xmlBuilder.append("</status>");
                xmlBuilder.append("\n\t<message>");
                xmlBuilder.append(MSG_METHOD_NOT_ALLOWED);
                xmlBuilder.append("</message>");
                xmlBuilder.append("\n</response>");
                context.getOut().print(xmlBuilder.toString());
            } else {
                context.getOut().print("{\"message\":\"" + MSG_METHOD_NOT_ALLOWED + "\",\"status\":"
                        + STATUS_METHOD_NOT_ALLOWED + "}");
            }
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processAuth() throws JspException {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new JspException(ACCESS_DENIED, new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
        }
        MasonRequest masonReq = (MasonRequest) request.getAttribute("mtgReq");
        authService = new AuthService();
        try {
            if (header.contains("Basic ")) {
                masonReq.setUid(authService.validateBasic(header, auth));
            } else if (header.contains(BEARER_)) {
                String bearerToken = header.replaceFirst(BEARER_, "");
                //check jwt format
                //validateJwt - check aud against val, exp
                masonReq.setUid(authService.validateBearer(bearerToken.trim(), auth));
            } else {
                throw new JspException(ACCESS_DENIED, new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
            }
        } catch (IllegalArgumentException ex) {
            throw new JspException(ACCESS_DENIED, new MetamugException(MetamugError.ROLE_ACCESS_DENIED));
        }
    }

}

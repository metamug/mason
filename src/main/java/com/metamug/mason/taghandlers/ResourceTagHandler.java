/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.taghandlers;

import com.metamug.mason.entity.request.MasonRequest;
import static com.metamug.mason.entity.response.MasonOutput.HEADER_JSON;
import com.metamug.mason.exception.MetamugError;
import com.metamug.mason.exception.MetamugException;
import com.metamug.mason.service.AuthService;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author anishhirlekar
 */
public class ResourceTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String auth;
    private AuthService authService;

    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final String MSG_METHOD_NOT_ALLOWED = "Method not allowed";
    public static final String ACCESS_DENIED = "Access Denied due to unauthorization";
    public static final String ACCESS_FORBIDDEN = "Access Denied due to unauthorization!";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String BEARER_ = "Bearer ";
    public static final String MASON_OUTPUT = "masonOutput";

    public void setAuth(String a) {
        auth = a;
    }

    @Override
    public int doStartTag() throws JspException {
        if (auth != null) {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            processAuth(request);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        process405(request, response, pageContext.getOut());

        return SKIP_PAGE;
    }

    private void process405(HttpServletRequest request, HttpServletResponse response, JspWriter out) {
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
                out.print(xmlBuilder.toString());
            } else {
                out.print("{\"message\":\"" + MSG_METHOD_NOT_ALLOWED + "\",\"status\":"
                        + STATUS_METHOD_NOT_ALLOWED + "}");
            }
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void processAuth(HttpServletRequest request) throws JspException {
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

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    }
}

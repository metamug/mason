/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.taghandlers.request;

import com.metamug.mason.Router;
import com.metamug.mason.entity.request.MasonRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author anishhirlekar
 */
public class RequestTagHandler extends BodyTagSupport implements TryCatchFinally {
    
    private String method;
    private boolean item;

    public RequestTagHandler(){
        super();
        method = null;
        item = false;
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
                    request.setAttribute(Router.REQUEST_HANDLED, Boolean.TRUE);
                    return EVAL_BODY_INCLUDE;                 
                }
            }
        } 
        return SKIP_BODY;
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import static javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author user
 */
public class RestTag extends BodyTagSupport implements TryCatchFinally {

    public static final String HEADER_ACCEPT = "Accept";
    public static final String MASON_OUTPUT = "masonOutput";

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Resource
    protected PageContext context; //For Mocking https://stackoverflow.com/a/17474381/1097600

    public RestTag() {
        super();
        this.context = pageContext;
    }

    @Override
    public int doStartTag() throws JspException {
        request = (HttpServletRequest) context.getRequest();
        response = (HttpServletResponse) context.getResponse();
        return 0;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    }

}

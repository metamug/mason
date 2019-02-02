/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author user
 */
public abstract class RestTag extends BodyTagSupport implements TryCatchFinally {

    @Resource
    protected PageContext context; //For Mocking https://stackoverflow.com/a/17474381/1097600
    
    public RestTag(){
        this.context = pageContext;
    }

}

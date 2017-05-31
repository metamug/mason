/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import com.metamug.api.common.MtgRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author Kaisteel
 */
public class ParentTagHandler extends BodyTagSupport {

    private String value;

    @Override
    public int doEndTag() throws JspException {
        MtgRequest mtg = (MtgRequest) pageContext.getRequest().getAttribute("mtgReq");
        if (mtg.getParent() != null && mtg.getParent().equalsIgnoreCase(value)) {
            throw new JspException("Parent resource not found", new ResourceNotFoundException(""));
        }
        return EVAL_PAGE;

    }

    public void setValue(String value) {
        if (value.isEmpty()) {
            this.value = null;
        } else {
            this.value = value;
        }
    }

    private static class ResourceNotFoundException extends Throwable {

        public ResourceNotFoundException(String string) {
        }
    }
}

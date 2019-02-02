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
import com.metamug.mason.tag.RestTag;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author anishhirlekar
 */
public class RequestTagHandler extends RestTag {

    private String method;
    private boolean item;
    private boolean processOutput;
    @Resource
    private MasonRequest masonReq;

    @Resource
    private LinkedHashMap<String, Object> resultMap;

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        masonReq = (MasonRequest) request.getAttribute("mtgReq");
        
        if (method.equalsIgnoreCase(masonReq.getMethod())) {
            processOutput = (masonReq.getId() != null) == item;
            if (processOutput) {
                resultMap = new LinkedHashMap<>();
                pageContext.setAttribute(MASON_OUTPUT, resultMap, PageContext.PAGE_SCOPE); 
                //changed from request scope to page scope
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        if (processOutput) {
            processOutput();
            return SKIP_PAGE;
        } else {
            return EVAL_PAGE;
        }
    }

    private void processOutput() {
        
        //added as an instance variable. Faster than pulling back from the map
        //LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) context.getAttribute(MASON_OUTPUT, PageContext.REQUEST_SCOPE);
        if (resultMap.isEmpty()) {
            response.setStatus(204);
            return;
        }

        String header = request.getHeader(HEADER_ACCEPT) == null ? HEADER_JSON : request.getHeader(HEADER_ACCEPT);
        MasonOutput output;
        List list = Arrays.asList(header.split("/"));
        if (list.contains("xml")) { //Accept: application/xml, text/xml
            output = new XMLOutput(resultMap);
        } else if (list.contains("json+dataset")) { //Accept: application/json+dataset
            output = new DatasetOutput(resultMap);
        } else { //Accept: application/json OR default
            output = new JSONOutput(resultMap);
        }

        String op = output.toString();
        response.setContentType(output.getContentType());
        context.setAttribute("Content-Length", op.length(), PageContext.REQUEST_SCOPE);
        try {
            context.getOut().print(op);
        } catch (IOException ex) {
            Logger.getLogger(ResourceTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMethod(String m) {
        method = m;
    }

    public void setItem(boolean i) {
        item = i;
    }  
}

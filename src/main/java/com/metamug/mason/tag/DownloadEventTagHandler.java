/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.mason.service.ConnectionProvider;
import javax.servlet.jsp.JspException;
import javax.sql.DataSource;

/**
 *
 * @author anishhirlekar
 */
public class DownloadEventTagHandler extends RestTag {
    
    private DataSource ds;
    
    @Override
    public int doEndTag() throws JspException {
        ds = ConnectionProvider.getMasonDatasource();
        String accept = request.getHeader("Accept");
    
        if(accept.contains("application/octet-stream")) {
            //TODO: Stream File object returned by Download event listener to http response
        }
        
        return EVAL_PAGE;
    }
}

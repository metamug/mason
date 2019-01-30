/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import com.metamug.mason.Router;
import static com.metamug.mason.Router.APPLICATION_HTML;
import static com.metamug.mason.Router.APPLICATION_JSON;
import static com.metamug.mason.Router.HEADER_CONTENT_TYPE;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */
public class MasonRequestFactory {
    public static MasonRequest create(HttpServletRequest request) throws IOException, ServletException{
        ParamExtractStrategy strategy;
        String contentType = request.getHeader(HEADER_CONTENT_TYPE) == null ? 
                Router.APPLICATION_FORM_URLENCODED : request.getHeader(HEADER_CONTENT_TYPE);
        
        if(contentType.contains(APPLICATION_JSON))
            strategy = new JsonStrategy(request);
        else if(contentType.contains("multipart/form-data"))
            strategy = new MultipartFormStrategy(request);
        else if(contentType.contains(APPLICATION_HTML))
            strategy = new HtmlStrategy(request);
        else
            strategy = new FormStrategy(request);
            
        return strategy.getRequest();
    }
}
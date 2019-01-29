/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import static com.metamug.mason.Router.APPLICATION_HTML;
import static com.metamug.mason.Router.APPLICATION_JSON;
import static com.metamug.mason.Router.HEADER_CONTENT_TYPE;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */
public class MasonRequestFactory {
    public static MasonRequest create(HttpServletRequest request){
        ParamExtractStrategy strategy;
        String contentType = request.getHeader(HEADER_CONTENT_TYPE) == null ? 
                APPLICATION_HTML : request.getHeader(HEADER_CONTENT_TYPE);
        switch(contentType){
            case APPLICATION_JSON:
                strategy = new JsonStrategy(request);
                break;
            default:
                strategy = new FormStrategy(request);
                break;
        }
        return strategy.getRequest();
    }
}

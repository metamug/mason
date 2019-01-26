/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import static com.metamug.mason.Router.APPLICATION_HTML;
import static com.metamug.mason.Router.APPLICATION_JSON;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */
public class MasonRequestFactory {
    public static MtgRequest create(HttpServletRequest request){
        ParamExtractStrategy strategy = new FormStrategy(request); //default is form strategy
        String contentType = request.getHeader("Content-Type") == null ? APPLICATION_HTML : request.getHeader("Content-Type");
        switch(contentType){
            case APPLICATION_JSON:
                strategy = new JsonStrategy(request);
                break;
            //@TODO ... Add more cases here    
        }
        return strategy.getRequest();
    }
}

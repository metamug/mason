/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */
public abstract class ParamExtractStrategy {

    protected MasonRequest mtgRequest = new MasonRequest();
    protected Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    
    public ParamExtractStrategy(HttpServletRequest request){    
        
    }
    
    public MasonRequest getRequest(){
        return mtgRequest;
    }
}

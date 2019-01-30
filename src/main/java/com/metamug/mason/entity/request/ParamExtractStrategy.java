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
 * @author D3ep4k
 */
public abstract class ParamExtractStrategy {
    protected MasonRequest masonRequest = new MasonRequest();
    protected Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    
    public ParamExtractStrategy(HttpServletRequest request){        
    }
    
    public MasonRequest getRequest(){
        masonRequest.setParams(params);
        return masonRequest;
    }
    
    /**
     * Add Key Value pair to Mason Request Object
     *
     * @param masonRequest
     * @param keyValue
     * @param params
     */
    protected static void addKeyPair(MasonRequest masonRequest, String[] keyValue, Map params) {
        if (keyValue[0].equalsIgnoreCase("id")) {
            masonRequest.setId(keyValue[1]);
        } else if (keyValue[0].equalsIgnoreCase("pid")) {
            masonRequest.setPid(keyValue[1]);
        } else if (keyValue[0].equalsIgnoreCase("uid")) {
            masonRequest.setUid(keyValue[1]);
        } else {
            params.put(keyValue[0], keyValue[1]);
        }
    }
}

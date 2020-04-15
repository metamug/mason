/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import com.metamug.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author D3ep4k
 */
public abstract class ParamExtractStrategy {

    protected Request masonRequest = new Request();
    protected Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public ParamExtractStrategy(HttpServletRequest request) {
    }

    public Request getRequest() {
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
    protected static void addKeyPair(Request masonRequest, String[] keyValue, Map params) {
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

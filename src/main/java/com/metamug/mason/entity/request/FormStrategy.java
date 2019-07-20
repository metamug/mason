/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author D3ep4k
 */
public class FormStrategy extends ParamExtractStrategy {

    public FormStrategy(HttpServletRequest request) {
        super(request);
        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String paramName = parameters.nextElement();
            if (paramName.trim().equalsIgnoreCase("id")) {
                masonRequest.setId(request.getParameter(paramName).trim());
            } else if (paramName.trim().equalsIgnoreCase("pid")) {
                masonRequest.setPid(request.getParameter(paramName).trim());
            } else if (paramName.trim().equalsIgnoreCase("uid")) {
                masonRequest.setUid(request.getParameter(paramName).trim());
            } else {
                if (request.getParameterValues(paramName).length > 1) {
                    params.put(paramName.trim(), String.join(",", request.getParameterValues(paramName)).trim());
                } else {
                    params.put(paramName.trim(), request.getParameter(paramName).trim().isEmpty() ? null : request.getParameter(paramName).trim());
                }
            }
        }
    }
}

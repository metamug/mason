/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author anishhirlekar
 */
public class HtmlStrategy extends ParamExtractStrategy {

    /**
     * @param request
     * @throws java.io.IOException
     */
    public HtmlStrategy(HttpServletRequest request) throws IOException {
        super(request);

        String line;
        StringBuilder data = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        }
        if (!data.toString().isEmpty()) {
            if (data.toString().split("&").length > 1) {
                for (String parameter : data.toString().split("&")) {
                    addKeyPair(masonRequest, parameter.split("="), params);
                }
            } else {
                addKeyPair(masonRequest, data.toString().split("="), params);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * @author anishhirlekar
 */
public class MultipartFormStrategy extends ParamExtractStrategy {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * @param request
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public MultipartFormStrategy(HttpServletRequest request) throws IOException, ServletException {
        super(request);
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            String line;
            StringBuilder data = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(part.getInputStream()))) {
                while ((line = br.readLine()) != null) {
                    data.append(line);
                }
            }
            params.put(part.getName(), data.toString());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import com.metamug.entity.Request;
import com.metamug.entity.Resource;
import static com.metamug.mason.Router.HEADER_CONTENT_TYPE;
import static com.metamug.mason.entity.request.FormStrategy.APPLICATION_FORM_URLENCODED;
import static com.metamug.mason.entity.request.HtmlStrategy.APPLICATION_HTML;
import static com.metamug.mason.entity.request.JsonStrategy.APPLICATION_JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */
public class RequestAdapter {

    public static Request create(HttpServletRequest request) throws IOException, ServletException {

        String path = request.getServletPath();
        String[] tokens = path.split("/");
        int versionTokenIndex = -1;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("^.*(v\\d+\\.\\d+).*$")) {
                versionTokenIndex = i;
                break;
            }
        }

        float version = Float.parseFloat(tokens[versionTokenIndex].substring(1));
        String resourceName;

        if (tokens.length == versionTokenIndex + 4 || tokens.length == versionTokenIndex + 5) {
            resourceName = tokens[versionTokenIndex + 3];
        } else {
            resourceName = tokens[versionTokenIndex + 1];
        }

        String method = request.getMethod().toLowerCase();

        ParamExtractStrategy strategy;
        String contentType = request.getHeader(HEADER_CONTENT_TYPE) == null
                ? APPLICATION_FORM_URLENCODED : request.getHeader(HEADER_CONTENT_TYPE);

        if (contentType.contains(APPLICATION_JSON)) {
            strategy = new JsonStrategy(request);
        } else if (contentType.contains("multipart/form-data")) {
            strategy = new MultipartFormStrategy(request);
        } else if (contentType.contains(APPLICATION_HTML)) {
            strategy = new HtmlStrategy(request);
        } else {
            strategy = new FormStrategy(request);
        }

        Request masonRequest = strategy.getRequest();

        //Set parent value and pid
        if (tokens.length == versionTokenIndex + VERSION_LENGTH || tokens.length == versionTokenIndex + VERSION_LENGTH + 1) {
            //@TODO get parent
            //masonRequest.setParent(parent);
            masonRequest.setPid(tokens[versionTokenIndex + 2]);
            masonRequest.setId((tokens.length > versionTokenIndex + 4) ? tokens[versionTokenIndex + 4] : null);
        } else {
            Resource resource = new Resource(resourceName, version, String.join("/", tokens), null);
            masonRequest.setResource(resource);
            masonRequest.setId((tokens.length > versionTokenIndex + 2) ? tokens[versionTokenIndex + 2] : null);
        }
        masonRequest.setMethod(method);
        masonRequest.setUri(tokens[versionTokenIndex + 1]);

        return new ImmutableRequest(masonRequest);
    }
    private static final int VERSION_LENGTH = 4;
}

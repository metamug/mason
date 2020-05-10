/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import com.metamug.entity.Request;
import com.metamug.entity.Resource;
import static com.metamug.mason.Router.HEADER_CONTENT_TYPE;
import static com.metamug.mason.Router.resourceFileExists;
import static com.metamug.mason.entity.request.FormStrategy.APPLICATION_FORM_URLENCODED;
import static com.metamug.mason.entity.request.HtmlStrategy.APPLICATION_HTML;
import static com.metamug.mason.entity.request.JsonStrategy.APPLICATION_JSON;
import static com.metamug.mason.entity.request.MultipartFormStrategy.MULTIPART_FORM_DATA;
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
        String[] tokens = path.split("/");  //without query string
        int versionTokenIndex = -1;

        //find index of version in the url
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("^.*(v\\d+\\.\\d+).*$")) {
                versionTokenIndex = i;
                break;
            }
        } //The uri may always not have a context. This can deal with no context as well.

        float version = Float.parseFloat(tokens[versionTokenIndex].substring(1)); //remove "v"
        String resourceName;

        if (tokens.length == versionTokenIndex + 4 || tokens.length == versionTokenIndex + 5) {
            resourceName = tokens[versionTokenIndex + CHILD_RESOURCE_OFFSET]; /* /backend/v1.0/resource-parent/2/resource */
        } else {
            resourceName = tokens[versionTokenIndex +  RESOURCE_OFFSET]; /* /backend/v1.0/resource */
        }

        String method = request.getMethod().toLowerCase();

        ParamExtractStrategy strategy;
        String contentType = request.getHeader(HEADER_CONTENT_TYPE) == null
                ? APPLICATION_FORM_URLENCODED : request.getHeader(HEADER_CONTENT_TYPE);

        if (contentType.contains(APPLICATION_JSON)) {
            strategy = new JsonStrategy(request);
        } else if (contentType.contains(MULTIPART_FORM_DATA)) {
            strategy = new MultipartFormStrategy(request);
        } else if (contentType.contains(APPLICATION_HTML)) {
            strategy = new HtmlStrategy(request);
        } else {
            strategy = new FormStrategy(request);
        }

        Request masonRequest = strategy.getRequest();
        Resource parentResource = null;
        //Set parent value and pid
        if (tokens.length > (versionTokenIndex + GROUP_RESOURCE_ID_OFFSET) ) {

            //@TODO get parent
            //masonRequest.setParent(parent);
            if(tokens.length == (versionTokenIndex + CHILD_RESOURCE_OFFSET) + 1){
                // /parent/pid/child/cid OR /parent/child/cid
                //check if parent exists
                String parentName = tokens[versionTokenIndex+ RESOURCE_OFFSET];
                if(resourceFileExists(parentName,version,request)) {
                    //this means /parent/pid/child/ - so set pid
                    masonRequest.setPid(tokens[versionTokenIndex + RESOURCE_ID_OFFSET]);
                } else {
                    //this means /parent/child/cid - so set cid
                    masonRequest.setId(tokens[versionTokenIndex + GROUP_RESOURCE_ID_OFFSET]);
                }
            } else {
                masonRequest.setPid(tokens[versionTokenIndex + RESOURCE_ID_OFFSET]);
                masonRequest.setId((tokens.length > versionTokenIndex + CHILD_RESOURCE_ID_OFFSET) ? tokens[versionTokenIndex + CHILD_RESOURCE_ID_OFFSET] : null);
            }
            //System.out.println("ID: "+masonRequest.getId());
            //System.out.println("PID: "+masonRequest.getPid());
        } else {            
            String parentName = tokens[versionTokenIndex + RESOURCE_OFFSET];
            if(resourceFileExists(parentName,version,request) ){
                masonRequest.setId((tokens.length > versionTokenIndex + RESOURCE_ID_OFFSET) ? tokens[versionTokenIndex + RESOURCE_ID_OFFSET] : null);
            } 
            //System.out.println("ID: "+masonRequest.getId());
            //System.out.println("PID: "+masonRequest.getPid());
        }

        Resource resource = new Resource(resourceName, version, String.join("/", tokens), parentResource); //@TODO why not use path from above
        masonRequest.setResource(resource);
        masonRequest.setMethod(method);
        masonRequest.setUri(tokens[versionTokenIndex +  RESOURCE_OFFSET]);

        return new ImmutableRequest(masonRequest);
    }

    private static final int VERSION_LENGTH = 4; // v1.3

    //below offsets are relative to version index
    private static final int RESOURCE_OFFSET = 1; // /v1.3/resource

    private static final int CHILD_RESOURCE_OFFSET = 3; // /v1.3/parent/32/resource
    private static final int GROUP_RESOURCE_ID_OFFSET = 3; // /v1.3/group/resource/32
    
    private static final int RESOURCE_ID_OFFSET = 2; // /v1.3/parent/32
    private static final int GROUP_RESOURCE_OFFSET = 2; // /v1.3/group/resource

    private static final int CHILD_RESOURCE_ID_OFFSET = 4; // /v1.3/parent/32/resource/32
    

}

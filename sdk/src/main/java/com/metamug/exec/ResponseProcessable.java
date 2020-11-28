package com.metamug.exec;

import com.metamug.entity.Response;

/**
 * 
 * @author themetamug
 */
public interface ResponseProcessable {
     /**
     * Performs operation response object from XRequest and other elements with Response
     * It is similar to ResultProcessable for Database Results
     * @param response Response object resulting from XRequest
     * @return Response data
     * @throws java.lang.Exception
     */
    public Response process(Response response) throws Exception;
}

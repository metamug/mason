package com.metamug.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Response return after processing the request
 * @param <T> can be String or an object implementing DTO interface
 */
public class Response <T> {

    private Map<String, String> headers = new HashMap<>();
    private T body;
    private int status;

    public Response() {
    }

    public Response(Map<String, String> headers, T body) {
        this(body);    
        this.headers = headers;
    }
    
    public Response(T body) {
        this.body = body;
        this.headers = new HashMap<>();
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

  
    public void setPayload(T body) {
        this.body = body;
    }
   
    /**
     *
     * @return InputStream allows response body to be a text or byte stream
     */
    public T getPayload() {
        return this.body;
    }

    public int getStatus() {
        return this.status;
    }
}

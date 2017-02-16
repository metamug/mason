/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.objectreturn.response;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anishhirlekar
 */
public class ObjectResponse {
    
    private int status;
    private final Map<String,String> headers;
    private Object data;   
    
    public ObjectResponse(){  
        this.headers = new HashMap<>();
    }
    
    public ObjectResponse(int status){
        this.status = status;
        this.headers = new HashMap<>();
    }
    
    public ObjectResponse(int status, Object data){
        this.status = status;
        this.data = data;
        this.headers = new HashMap<>();
    }
    
    public void setStatus(int status){
        this.status = status;
    }
    
    public int getStatus(){
        return this.status;
    }
    
    public void addHeader(String name, String value){
        headers.put(name, value);
    }
    
    public Map<String,String> getHeaders(){
        return this.headers;
    }
    
    public void setData(Object data){
        this.data = data;
    }
    
    public Object getData(){
        return this.data;
    }
}

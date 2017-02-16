/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.objectreturn.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anishhirlekar
 */
public class ListResponse {
    
    private int status;
    private final Map<String,String> headers;
    private List<?> data;   
    
    public ListResponse(){  
        this.headers = new HashMap<>();
    }
    
    public ListResponse(int status){
        this.status = status;
        this.headers = new HashMap<>();
    }
    
    public ListResponse(int status, List<?> data){
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
    
    public void setData(List<?> data){
        this.data = data;
    }
    
    public List<?> getData(){
        return this.data;
    }
}

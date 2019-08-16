/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import com.jayway.jsonpath.JsonPath;
import java.sql.ResultSet;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ResolveService {
    public String read(Object source, String path){
        if(source instanceof ResultSet){
            //System.out.println("ResultSet");
        }else if(source instanceof JSONObject){
            path = "$"+path;
            Object value = JsonPath.parse(source.toString()).read(path);
            return value.toString();
        }
        return null;
    }
    
}
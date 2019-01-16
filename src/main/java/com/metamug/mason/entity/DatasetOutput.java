/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity;

import java.util.Map;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;

/**
 *
 * @author anishhirlekar
 */
public class DatasetOutput extends JSONOutput {
    
    public DatasetOutput(Map<String, Object> outputMap) {
        if(outputMap.isEmpty())
            responseJson.put("response", new JSONArray());
        
        for(Map.Entry<String, Object> entry: outputMap.entrySet()){
            Object obj = entry.getValue();
            
            if(obj instanceof ResultImpl){
                responseJson.append("response",convertSQLResultToDataset((ResultImpl)obj));
            } else{
                responseJson.append("response",obj);
            }
        }        
    }      
      
}

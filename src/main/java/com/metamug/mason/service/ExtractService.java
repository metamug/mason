/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import com.metamug.mason.io.mpath.Extractor;
import com.metamug.mason.io.mpath.ExpressionExtractStrategy;
import com.metamug.mason.io.mpath.ExtractStrategy;
import com.metamug.mason.io.mpath.JSONExtractStrategy;
import com.metamug.mason.io.mpath.ResultExtractStrategy;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ExtractService {
    public String extract(Object target, String path){
        ExtractStrategy strategy;
        
        if(target instanceof ResultImpl){
            strategy = new ResultExtractStrategy();
        }else if(target instanceof JSONObject){
            strategy = new JSONExtractStrategy();
        }else{
            strategy = new ExpressionExtractStrategy();
        }
        
        Extractor extractor = new Extractor(path, strategy, target);
        return extractor.extract();
    }
    
}
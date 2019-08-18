/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import com.metamug.mason.io.mpath.extract.Extractor;
import com.metamug.mason.io.mpath.extract.strategy.BeanObjectExtractStrategy;
import com.metamug.mason.io.mpath.extract.strategy.ExtractStrategy;
import com.metamug.mason.io.mpath.extract.strategy.JSONExtractStrategy;
import com.metamug.mason.io.mpath.extract.strategy.ResultSetExtractStrategy;
import java.sql.ResultSet;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ExtractService {
    public String extract(Object target, String path){
        ExtractStrategy strategy;
        
        if(target instanceof ResultSet){
            strategy = new ResultSetExtractStrategy();
        }else if(target instanceof JSONObject){
            strategy = new JSONExtractStrategy();
        }else{
            strategy = new BeanObjectExtractStrategy();
        }
        
        Extractor extractor = new Extractor(path, strategy, target);
        return extractor.extract();
    }
    
}
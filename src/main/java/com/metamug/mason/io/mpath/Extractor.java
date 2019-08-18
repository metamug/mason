/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import com.metamug.mason.io.mpath.ExtractStrategy;

/**
 * Extract value based on mpath 
 * @author themetamug
 */
public class Extractor {

    private ExtractStrategy strategy;
    private String path;
    private Object target;
    
    public Extractor(String path){
        this.path = path;
    }
    
    public Extractor(String path, ExtractStrategy strategy, Object target){
        this.path = path;
        this.strategy = strategy;
        this.target = target;
    }
    
    public void setStategy(ExtractStrategy strategy){
        this.strategy = strategy;
    }
    
    public String extract(){
        return this.strategy.extract(path, target);
    }
}

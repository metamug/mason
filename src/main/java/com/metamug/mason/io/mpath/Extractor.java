/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

/**
 * Extract value based on mpath 
 * @author themetamug
 */
public class Extractor {

    private ExtractStrategy stategy;
    private String path;
    
    public Extractor(String path){
        this.path = path;
    }
    
    public Extractor(String path, ExtractStrategy strategy){
        this.path = path;
        this.stategy = strategy;
    }
    
    public void setStategy(ExtractStrategy strategy){
        this.stategy = strategy;
    }
    
    public String extract(){
        return this.stategy.extract(path);
    }
}

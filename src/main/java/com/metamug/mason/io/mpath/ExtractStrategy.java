package com.metamug.mason.io.mpath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pc
 */
public abstract class ExtractStrategy<T> {
    
    /**
     * Extract value based on path provided
     * @param path Path for look up
     * @param target Target Object
     * @return 
     */
    public abstract String extract(String path, T target);
    
    /**
     * Method takes MPath value and returns locator string
     *
     * @param path MPath string
     * @return locator string
     */
    protected static String getLocator(String path){
        return path.replaceFirst("\\$\\[(.*?)\\]","");
    }
    
    /**
     * Method takes MPath value and returns var name
     *
     * @param path MPath string
     * @return var object name
     */
    public static String getVarName(String path){
        Pattern p = Pattern.compile("\\[(.*?)\\]");//['str1'],['str2'],...
        Matcher m = p.matcher(path);
        String name = null;
        
        while(m.find()) {
            name = m.group(1);
        }
        
        return name;
    }
    
    
}

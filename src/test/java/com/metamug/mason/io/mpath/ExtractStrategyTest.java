/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class ExtractStrategyTest {
    
    @Test
    public void varNameTest1(){
        String path = "$[xreq].body.arr[1].title";
        
        String var = ExtractStrategy.getVarName(path);
        
        Assert.assertEquals("xreq", var);
    }
    @Test
    public void varNameTest2(){
        String path = "$[getCustomers][1].name";
        
        String var = ExtractStrategy.getVarName(path);
        
        Assert.assertEquals("getCustomers", var);
    }
    @Test
    public void locatorTest1(){
        String path = "$[xreq].body.arr[1].title";
        
        String l = ExtractStrategy.getLocator(path);
        
        Assert.assertEquals(".body.arr[1].title",l);
    }
    
    @Test
    public void locatorTest2(){
        String path = "$[getCustomers][1].name";
        
        String rowIndex = ExtractStrategy.getLocator(path);
        
        Assert.assertEquals("[1].name", rowIndex);
    }
}

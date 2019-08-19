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
public class MPathExtractionTest {
    
    @Test
    public void varNameTest(){
        String path = "$['xreq'].body.arr[1].title";
        
        String var = MPathUtil.getVarName(path);
        
        Assert.assertEquals("xreq", var);
    }
    
    @Test
    public void locatorTest1(){
        String path = "$['xreq'].body.arr[1].title";
        
        String l = MPathUtil.getLocator(path);
        
        Assert.assertEquals(".body.arr[1].title",l);
    }
    
    @Test
    public void locatorTest2(){
        String path = "$['getCustomers'][1].name";
        
        String rowIndex = MPathUtil.getLocator(path);
        
        Assert.assertEquals("[1].name", rowIndex);
    }
    
    @Test
    public void rowTest1(){
        String path = "$['getCustomers'][1].name";
        
        String rowIndex = MPathUtil.getRow(path);
        
        Assert.assertEquals("1", rowIndex);
    }
    
    @Test
    public void rowTest2(){
        String path = "$['getCustomers'].name";
        
        String rowIndex = MPathUtil.getRow(path);
        
        Assert.assertEquals("0", rowIndex);
    }
    
    @Test
    public void rowTest3(){
        String path = "$['getCustomers'][14].enterprise[11].address";
        
        String rowIndex = MPathUtil.getRow(path);
        
        Assert.assertEquals("14", rowIndex);
    }
    
    @Test
    public void columnTest1(){
        String path = "$['getCustomers'][1].name";
        
        String col = MPathUtil.getColumn(path);
        Assert.assertEquals("name",col);
    }
    
    @Test
    public void columnTest2(){
        String path = "$['getCustomers'].name";
        
        String col = MPathUtil.getColumn(path);
        Assert.assertEquals("name",col);
    }
    @Test
    public void columnTest3(){
        String path = "$['getCustomers'][1].enterprise[11].address";
        
        String col = MPathUtil.getColumn(path);
        
        Assert.assertEquals("enterprise[11].address",col);
    }
}

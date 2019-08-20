/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class ResultExtractStrategyTest {
    ResultExtractStrategy st;
    
    @Before
    public void init(){
        st = new ResultExtractStrategy();
    }
    
    @Test
    public void rowTest1(){
        String path = "$[getCustomers][1].name";
        
        String rowIndex = st.getRow(path);
        
        Assert.assertEquals("1", rowIndex);
    }
    
    @Test
    public void rowTest2(){
        String path = "$[getCustomers].name";
        
        String rowIndex = st.getRow(path);
        
        Assert.assertEquals("0", rowIndex);
    }
    
    @Test
    public void rowTest3(){
        String path = "$[getCustomers][14].enterprise[11].address";
        
        String rowIndex = st.getRow(path);
        
        Assert.assertEquals("14", rowIndex);
    }
    
    @Test
    public void columnTest1(){
        String path = "$[getCustomers][1].name";
        
        String col = st.getColumn(path);
        Assert.assertEquals("name",col);
    }
    
    @Test
    public void columnTest2(){
        String path = "$[getCustomers].name";
        
        String col = st.getColumn(path);
        Assert.assertEquals("name",col);
    }
    @Test
    public void columnTest3(){
        String path = "$[getCustomers][1].enterprise[11].address";
        
        String col = st.getColumn(path);
        
        Assert.assertEquals("enterprise[11].address",col);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author anishhirlekar
 */
public class ResultExtractStrategyTest {
    ResultExtractStrategy st;
    
    private ResultImpl resultImpl;
    
    @Before
    public void init(){
        st = new ResultExtractStrategy();
        
        SortedMap row1 = new TreeMap();
        row1.put("name", "John");
        row1.put("age", "26");
        
        SortedMap row2 = new TreeMap();
        row2.put("name", "Robert");
        row2.put("age", "66");
        
        SortedMap[] res = new SortedMap[]{row1,row2};
        
        resultImpl = mock(ResultImpl.class);
        
        when(resultImpl.getRows()).thenReturn(res);
        when(resultImpl.getColumnNames()).thenReturn(new String[]{"name", "age"});
        when(resultImpl.getRowCount()).thenReturn(2);
    }
    
    @Test
    public void test1(){
        String path = "$[getCustomers][1].name";
        String value = st.extract(path, resultImpl);
        Assert.assertEquals("Robert", value);
    }
    
    @Test
    public void test2(){
        String path = "$[getCustomers][0].age";
        String value = st.extract(path, resultImpl);
        Assert.assertEquals("26", value);
    }
}

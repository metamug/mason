/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import com.metamug.mason.io.mpath.TestData;
import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class ConvertServiceTest {
    private ConvertService cs;
    LinkedHashMap<String,Object> map;
    
    @Before
    public void init(){
        map = new LinkedHashMap<>();   
    }
    
    @Test
    public void convertJsonToMap(){
        cs = new ConvertService();
        cs.convertToMap(TestData.TEST_JSON3, map, "testJson");
        System.out.println(map);
    }
   
    @Test
    public void convertStringToMap(){
        cs = new ConvertService();
        cs.convertToMap("Hello World!", map, "testStr");
        System.out.println(map);
    }
    
    //todo: test resultimpl to map using mockito
}

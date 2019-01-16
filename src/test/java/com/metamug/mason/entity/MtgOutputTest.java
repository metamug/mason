/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class MtgOutputTest {
    private Map<String,Object> outputMap;
    private final String sampleObj = "{ \"name\":\"John\", \"age\":30, \"car\":null }";
    private final String sampleArray = "[\n" +
                        "    { \"name\":\"Ford\", \"models\":[ \"Fiesta\", \"Focus\", \"Mustang\" ] },\n" +
                        "    { \"name\":\"BMW\", \"models\":[ \"320\", \"X3\", \"X5\" ] },\n" +
                        "    { \"name\":\"Fiat\", \"models\":[ \"500\", \"Panda\" ] }\n" +
                        "  ]";
    
    @Before
    public void setup(){
        outputMap = new LinkedHashMap<>();
        outputMap.put("res1", new JSONObject(sampleObj));
        outputMap.put("res2", new JSONArray(sampleArray));
        outputMap.put("res3", "Hello World");
    }
    
    @Test
    public void testJsonOutput(){
        JSONOutput jsonOutput = new JSONOutput(outputMap);
        JSONArray resArray = new JSONArray(jsonOutput.toString());
        System.out.println(resArray.toString());
    }
}

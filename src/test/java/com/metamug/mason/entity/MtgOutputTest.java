/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @Mock
    private ResultImpl sampleResult;
    
    @Before
    public void setup(){
        sampleResult = mock(ResultImpl.class);
        String[] colNames = {"name","age","car"};
        SortedMap[] rows = getSampleRows();
        
        when(sampleResult.getColumnNames()).thenReturn(colNames);
        when(sampleResult.getRows()).thenReturn(rows);
        
        outputMap = new LinkedHashMap<>();
        outputMap.put("res1", new JSONObject(sampleObj));
        outputMap.put("res2", new JSONArray(sampleArray));
        outputMap.put("res3", "Hello World");
        outputMap.put("res4",sampleResult);        
    }
    
    @Test
    public void testJsonOutput(){
        JSONOutput jsonOutput = new JSONOutput(outputMap);
        System.out.println(jsonOutput.length());
        JSONArray resArray = new JSONArray(jsonOutput.generateOutputString());
        System.out.println(resArray.toString());
    }
    
    private SortedMap[] getSampleRows(){
        SortedMap<String,String> row1 = new TreeMap<>();
        row1.put("name","John");
        row1.put("age", "30");
        row1.put("car", "Ford");
        
        SortedMap<String,String> row2 = new TreeMap<>();
        row2.put("name","Sean");
        row2.put("age", "40");
        row2.put("car", "BMW");
        
        SortedMap[] rows = {row1,row2};
        return rows;
    }
}

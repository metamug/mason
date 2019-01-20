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
import org.json.XML;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author anishhirlekar
 */
public class MasonOutputTest {

    private Map<String, Object> outputMap;
    private final String sampleObj = "{ \"name\":\"John\", \"age\":30, \"car\":null }";
    private final String sampleArray = "[\n"
            + "    { \"name\":\"Ford\", \"models\":[ \"Fiesta\", \"Focus\", \"Mustang\" ] },\n"
            + "    { \"name\":\"BMW\", \"models\":[ \"320\", \"X3\", \"X5\" ] },\n"
            + "    { \"name\":\"Fiat\", \"models\":[ \"500\", \"Panda\" ] }\n"
            + "  ]";
    @Mock
    private ResultImpl sampleResult;

    @Before
    public void setup() {
        sampleResult = mock(ResultImpl.class);
        String[] colNames = {"name", "age", "car"};
        SortedMap[] rows = getSampleRows();

        when(sampleResult.getColumnNames()).thenReturn(colNames);
        when(sampleResult.getRows()).thenReturn(rows);

        outputMap = new LinkedHashMap<>();
        outputMap.put("res1", new JSONObject(sampleObj));
        outputMap.put("res2", new JSONArray(sampleArray));
        outputMap.put("res3", "Hello World");
        outputMap.put("res4", sampleResult);
    }

    @Test
    public void testJson(){
        String dataType = MasonOutput.HEADER_JSON;
        MasonOutput output = getOutput(dataType);
        String outStr = output.toString();
        System.out.println("json: "+outStr);
        System.out.println("Length: "+outStr.length());
        Assert.assertTrue(outStr.length()>1);
    }
    
    @Test
    public void testJsonSingleResult(){
        Map<String, Object> singleMap = new LinkedHashMap<>();   
        singleMap.put("res2", new JSONArray(sampleArray));
        MasonOutput output = new JSONOutput(singleMap);
        String outStr = output.toString();
        System.out.println("single json: "+outStr);
        System.out.println("Length: "+outStr.length());
        //@TODO how do you know its a single json object. Do you want to look
        //everytime when you run the test. Validate the string back to JSON Object
        Assert.assertTrue(outStr.length()>1);
    }

    @Test
    public void testDataSet(){
        String dataType = MasonOutput.HEADER_DATASET;
        MasonOutput output = getOutput(dataType);
        String outStr = output.toString();
        System.out.println("DATASET: "+outStr);
        System.out.println("Length: "+outStr.length());
        Assert.assertTrue(outStr.length()>1);
    }

    @Test
    public void testXml(){
        String dataType = MasonOutput.HEADER_XML;
        MasonOutput output = getOutput(dataType);
        String outStr = output.toString();
        System.out.println("XML: "+outStr);
        System.out.println("Length: "+outStr.length());
        XML.toJSONObject(outStr); //validate xml 
        Assert.assertTrue(outStr.length()>1);
    }

    private MasonOutput getOutput(String dataType){
        MasonOutput output = new JSONOutput(new LinkedHashMap<>());
        Assert.assertEquals("[]", output.toString());

        switch(dataType){
            case MasonOutput.HEADER_JSON:
                output = new JSONOutput(outputMap);
                break;
            case MasonOutput.HEADER_DATASET:
                output = new DatasetOutput(outputMap);
                break;
            case MasonOutput.HEADER_XML:
                output = new XMLOutput(outputMap);
                break;
        }
        return output;
    }

    private SortedMap[] getSampleRows() {
        SortedMap<String, String> row1 = new TreeMap<>();
        row1.put("name", "John");
        row1.put("age", "30");
        row1.put("car", "Ford");

        SortedMap<String, String> row2 = new TreeMap<>();
        row2.put("name", "Sean");
        row2.put("age", "40");
        row2.put("car", "BMW");

        SortedMap[] rows = {row1, row2};
        return rows;
    }
}

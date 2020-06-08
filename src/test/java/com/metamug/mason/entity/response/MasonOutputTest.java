/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.response;

import com.metamug.entity.Response;
import com.metamug.mason.processables.Customer;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.bind.JAXBException;
import java.util.*;
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.when;

/**
 * @author anishhirlekar
 */
@RunWith(MockitoJUnitRunner.class)
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

        String[] colNames = {"name", "age", "car"};
        SortedMap[] rows = getSampleRows();

        when(sampleResult.getColumnNames()).thenReturn(colNames);
        when(sampleResult.getRows()).thenReturn(rows);

        List<Object> list = new ArrayList<>();
        list.add("I am String");
        list.add(new JSONObject(sampleObj));
        list.add(sampleResult);
        list.add(null);

        Customer customer = new Customer();
        customer.setName("John");
        customer.setId(1);
        customer.setContact("8080808080", "john@example.com");

        list.add(customer);

        outputMap = new LinkedHashMap<>();
        outputMap.put("jsonobj", new JSONObject(sampleObj));
        outputMap.put("jsonarray", new JSONArray(sampleArray));
        outputMap.put("string", "Hello World");
        outputMap.put("result", sampleResult);
        outputMap.put("null", null);
        outputMap.put("pojo", customer);
        outputMap.put("list", list);
    }

    @Test
    public void testJson() throws JAXBException {
        String dataType = MediaType.APPLICATION_JSON;
        String outStr = getOutput(dataType);
        System.out.println("json: " + outStr);
        System.out.println("Length: " + outStr.length());
        assertTrue(outStr.length()>500);
    }

    @Test
    public void testDataSet() throws JAXBException {
        String dataType = MasonOutput.HEADER_DATASET;
        String outStr = getOutput(dataType);
        System.out.println("DATASET: " + outStr);
        System.out.println("Length: " + outStr.length());
        assertTrue(outStr.length()>500);
    }

    @Test
    public void testXml() throws JAXBException {
        String dataType = MediaType.APPLICATION_XML;
        String outStr = getOutput(dataType);
        System.out.println("XML: " + outStr);
        System.out.println("Length: " + outStr.length());
        XML.toJSONObject(outStr); //validate xml 
        assertTrue(outStr.length()>800);
    }

    private String getOutput(String dataType) throws JAXBException {
        MasonOutput output = null;

        switch (dataType) {
            case MediaType.APPLICATION_JSON:
                output = new JSONOutput();
                break;
            case MasonOutput.HEADER_DATASET:
                output = new DatasetOutput();
                break;
            case MediaType.APPLICATION_XML:
                output = new XMLOutput();
                break;
        }
        
        Response res = output.generate(null, outputMap);
        
//        Assert.assertEquals(null, res.getPayload());
        return output.format(res);
    }

    public static SortedMap[] getSampleRows() {
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

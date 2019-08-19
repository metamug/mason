/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.io.mpath;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class JSONExtractStrategyTest {
    private static final String json = "{\n" +
"    \"store\": {\n" +
"        \"book\": [\n" +
"            {\n" +
"                \"category\": \"reference\",\n" +
"                \"author\": \"Nigel Rees\",\n" +
"                \"title\": \"Sayings of the Century\",\n" +
"                \"price\": 8.95\n" +
"            },\n" +
"            {\n" +
"                \"category\": \"fiction\",\n" +
"                \"author\": \"Evelyn Waugh\",\n" +
"                \"title\": \"Sword of Honour\",\n" +
"                \"price\": 12.99\n" +
"            },\n" +
"            {\n" +
"                \"category\": \"fiction\",\n" +
"                \"author\": \"Herman Melville\",\n" +
"                \"title\": \"Moby Dick\",\n" +
"                \"isbn\": \"0-553-21311-3\",\n" +
"                \"price\": 8.99\n" +
"            },\n" +
"            {\n" +
"                \"category\": \"fiction\",\n" +
"                \"author\": \"J. R. R. Tolkien\",\n" +
"                \"title\": \"The Lord of the Rings\",\n" +
"                \"isbn\": \"0-395-19395-8\",\n" +
"                \"price\": 22.99\n" +
"            }\n" +
"        ],\n" +
"        \"bicycle\": {\n" +
"            \"color\": \"red\",\n" +
"            \"price\": 19.95\n" +
"        }\n" +
"    },\n" +
"    \"expensive\": 10\n" +
"}";
    
    JSONExtractStrategy st;
    
    @Before
    public void init(){
        st = new JSONExtractStrategy();
    }
    
    @Test
    public void test1(){
        String path = "$[xreq].store.book[0].title";
        JSONObject j = new JSONObject(json.trim());
        //System.out.println(j.getJSONObject("store").toString());
        String val = st.extract(path,j);
        System.out.println(val);
    }
}

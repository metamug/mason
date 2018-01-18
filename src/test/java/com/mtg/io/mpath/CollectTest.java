/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.mpath;

import org.json.JSONArray;
import org.junit.Assert;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class CollectTest {
    public static final String inputJson1 = "[\n" +
"       {\"name\":\"Anish\", \"books\":{\"name\":\"book2\"}}, \n" +
"       {\"name\":\"Anish\", \"books\":{\"name\":\"book4\"}}, \n" +
"       {\"name\":\"Anish\", \"books\":{\"name\":\"book5\"}}, \n" +
"       {\"name\":\"Anish\", \"books\":{\"name\":\"book6\"}}\n" +
"    ]";
    
    @Test
    public void TestWhetherNotJsonArray(){
        JSONObject obj = new JSONObject();
        obj.put("name", "Anish");
        obj.put("age", "22");
                
        Object name = obj.get("name");
        Assert.assertFalse(name instanceof JSONArray);
    }
    
    @Test
    public void TestWhetherJsonArray(){
        JSONObject obj = new JSONObject();
        JSONArray names = new JSONArray();
        names.put("Anish");
        names.put("Deepak");
        names.put("Kaustubh");
        obj.put("name", names);
        obj.put("age", "22");
                
        Object name = obj.get("name");
        Assert.assertTrue(name instanceof JSONArray);
    }
    
    @Test
    public void TestCollect(){
        JSONObject object = MPathUtil.collect(new JSONArray(inputJson1));
        System.out.println(object);
    }
}

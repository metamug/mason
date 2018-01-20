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

    private static final String INPUT_JSON = "[\n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book1\",\"price\":\"10\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book2\",\"price\":\"11\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book3\",\"price\":\"12\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book4\",\"price\":\"13\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book5\",\"price\":\"14\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book6\",\"price\":\"15\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book7\",\"price\":\"16\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book8\",\"price\":\"17\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book9\",\"price\":\"18\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book10\",\"price\":\"19\"},\"number\":\"8080\"}\n"
            + "    ]";

    // @Test
    public void TestWhetherNotJsonArray() {
        JSONObject obj = new JSONObject();
        obj.put("name", "Anish");
        obj.put("age", "22");

        Object name = obj.get("name");
        Assert.assertFalse(name instanceof JSONArray);
    }

    //@Test
    public void TestWhetherJsonArray() {
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
    public void TestCollect() {
        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON));
        System.out.println(object);
    }
}

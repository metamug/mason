/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.mpath;

import org.json.JSONArray;
import org.junit.Assert;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class CollectTest {

    private static final String INPUT_JSON_1 = "[\n"
            + "   {\"name\":\"Anish\", \"books\":{\"name\":\"book1\"}}, \n"
            + "   {\"name\":\"Anish\", \"books\":{\"name\":\"book2\"}}, \n"
            + "   {\"name\":\"Anish\", \"books\":{\"name\":\"book3\"}}, \n"
            + "   {\"name\":\"Anish\", \"books\":{\"name\":\"book4\"}}, \n"
            + "   {\"name\":\"Anish\", \"books\":{\"name\":\"book5\"}}  \n"
            + "]";
    private static final String INPUT_JSON_2 = "[\n"
            + "   {\"name\":\"Anish1\", \"books\":{\"name\":\"book1\"}}, \n"
            + "   {\"name\":\"Anish2\", \"books\":{\"name\":\"book2\"}}, \n"
            + "   {\"name\":\"Anish3\", \"books\":{\"name\":\"book3\"}}, \n"
            + "   {\"name\":\"Anish4\", \"books\":{\"name\":\"book4\"}}, \n"
            + "   {\"name\":\"Anish5\", \"books\":{\"name\":\"book5\"}}, \n"
            + "]";

    private static final String INPUT_JSON_3 = "[\n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book1\",\"price\":\"10\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book2\",\"price\":\"11\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book3\",\"price\":\"12\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book4\",\"price\":\"13\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book5\",\"price\":\"14\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book6\",\"price\":\"15\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book7\",\"price\":\"16\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book8\",\"price\":\"17\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book9\",\"price\":\"18\"},\"number\":\"8080\"}, \n"
            + "       {\"name\":\"Anish\", \"books\":{\"name\":\"book10\",\"price\":\"19\"},\"number\":\"8080\"} \n"
            + "    ]";

    @Ignore
    @Test
    public void TestWhetherNotJsonArray() {
        JSONObject obj = new JSONObject();
        obj.put("name", "Anish");
        obj.put("age", "22");

        Object name = obj.get("name");
        Assert.assertFalse(name instanceof JSONArray);
    }

    @Ignore
    @Test
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

    @Ignore
    @Test
    public void TestCollect1() {
        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON_1));
        System.out.println(object);
    }

    @Test
    public void TestCollect2() {
        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON_2));
        System.out.println(object);
    }

    @Ignore
    @Test
    public void TestCollect3() {
        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON_3));
        System.out.println(object);
    }
}

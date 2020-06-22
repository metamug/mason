//package com.metamug.mason.io.mpath;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// *
// * @author anishhirlekar
// */
//public class CollectTest {
//
//    private static final String INPUT_JSON_ARRAY = "[\n"
//            + "   {\"name\":\"Person1\", \"books\":{\"name\":\"book1\"}}, \n"
//            + "   {\"name\":\"Person1\", \"books\":{\"name\":\"book2\"}}, \n"
//            + "   {\"name\":\"Person1\", \"books\":{\"name\":\"book3\"}}, \n"
//            + "   {\"name\":\"Person1\", \"books\":{\"name\":\"book4\"}}, \n"
//            + "   {\"name\":\"Person1\", \"books\":{\"name\":\"book5\"}}  \n"
//            + "]";
//
//    private static final String INPUT_JSON_ARRAY_SINGLE = "[\n"
//            + "       {\"name\":\"Person1\", \"books\":{\"name\":\"book1\",\"price\":\"10\"},\"number\":\"8080\"}, \n"
//            + "       ]";
//
//    @Test
//    public void TestCollectArray() {
//        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON_ARRAY));
//        JSONArray array = object.getJSONArray("books");
//        String bookName = array.getJSONObject(0).getString("name");
//        Assert.assertEquals("book1", bookName);
//    }
//
//    @Test
//    public void TestEmptyArray() {
//        JSONObject object = MPathUtil.collect(new JSONArray());
//        Assert.assertNull(object);
//    }
//
//    @Test
//    public void TestSingularArray() {
//        JSONObject object = MPathUtil.collect(new JSONArray(INPUT_JSON_ARRAY_SINGLE));
//        String bookName = object.getJSONObject("books").getString("name");
//
//        Assert.assertEquals("book1", bookName);
//    }
//
//}

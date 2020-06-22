//package com.metamug.mason.io.mpath;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.junit.Assert;
//import org.junit.Test;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.xpath.XPathExpressionException;
//import java.io.IOException;
//
///**
// *
// * @author anishhirlekar
// */
//public class MPathFetchValueTest {
//
//    @Test
//    public void TestCase1() throws XPathExpressionException, IOException,
//            SAXException, ParserConfigurationException {
//        String testJson = TestData.TEST_JSON;
//
//        String equivalentXml = XML.toString(new JSONObject(testJson));
//        String mKey1 = "Port.ExtendedProperties.Property[0].D";
//        String mKey2 = "Port.ExtendedProperties.Property[1].D";
//        String mKey3 = "Port.ThreadPool.Max";
//        Object jsonVal1 = MPathUtil.getValueFromJson(testJson, mKey1);
//        Object jsonVal2 = MPathUtil.getValueFromJson(testJson, mKey2);
//        Object jsonVal3 = MPathUtil.getValueFromJson(testJson, mKey3);
//
//        //System.out.println("TEST_JSON: \n"+TEST_JSON);
//        Object xmlVal1 = MPathUtil.getValueFromXml(equivalentXml, mKey1);
//        Object xmlVal2 = MPathUtil.getValueFromXml(equivalentXml, mKey2);
//        Object xmlVal3 = MPathUtil.getValueFromXml(equivalentXml, mKey3);
//        /*
//        System.out.println("x1: "+xmlVal1);
//        System.out.println("x2: "+xmlVal2);
//        System.out.println("x Val 3: "+xmlVal3);
//         */
//        Assert.assertArrayEquals(new String[]{jsonVal1.toString(), jsonVal2.toString(), jsonVal3.toString()},
//                new Object[]{xmlVal1.toString(), xmlVal2.toString(), xmlVal3.toString()});
//
//    }
//
//    @Test
//    public void TestCase2() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
//        String testXml = TestData.TEST_XML;
//
//        String equivalentJson = (XML.toJSONObject(testXml)).toString();
//        String mKey1 = "Resource.Request[0].method";
//        String mKey2Xml = "Resource.Request[1].Sql";
//        String mKey2Json = "Resource.Request[1].Sql.content";
//        String mKey3 = "Resource.Request[0].Sql[1].content";
//        String mKey4 = "Resource.Desc";
//        String mKey5 = "Resource.version";
//        //System.out.println(TEST_XML);
//        String xmlVal1 = (MPathUtil.getValueFromXml(testXml, mKey1)).toString();
//        String xmlVal2 = (MPathUtil.getValueFromXml(testXml, mKey2Xml)).toString();
//        String jsonVal1 = (MPathUtil.getValueFromJson(equivalentJson, mKey1)).toString();
//        String jsonVal2 = (MPathUtil.getValueFromJson(equivalentJson, mKey2Json)).toString();
//        String xmlVal3 = (MPathUtil.getValueFromXml(testXml, mKey3)).toString();
//        String jsonVal3 = (MPathUtil.getValueFromJson(equivalentJson, mKey3)).toString();
//        String xmlVal4 = (MPathUtil.getValueFromXml(testXml, mKey4)).toString();
//        String jsonVal4 = (MPathUtil.getValueFromJson(equivalentJson, mKey4)).toString();
//        String xmlVal5 = (MPathUtil.getValueFromXml(testXml, mKey5)).toString();
//        String jsonVal5 = (MPathUtil.getValueFromJson(equivalentJson, mKey5)).toString();
//
//        Assert.assertArrayEquals(new String[]{xmlVal1, xmlVal2, xmlVal3, xmlVal4, xmlVal5},
//                new String[]{jsonVal1, jsonVal2, jsonVal3, jsonVal4, jsonVal5});
//    }
//
//    @Test
//    public void FailCase1() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
//        //System.out.println(TEST_XML2);
//        String mKey1 = "Resource.Request.Sql[1]";
//        Assert.assertNull(MPathUtil.getValueFromXml(TestData.TEST_XML2, mKey1));
//    }
//
//    @Test
//    public void FailCase2() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
//        String garbageMKey = "ChangeMeToTestGarbageValues";
//        Assert.assertNull(MPathUtil.getValueFromXml(TestData.TEST_XML, garbageMKey));
//    }
//
//    @Test
//    public void FailCase3() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
//        String mPath = "[1].Port.ThreadPool.Max";
//        Object valueFromJson = MPathUtil.getValueFromJson(TestData.TEST_JSON2, mPath);
//        //xml without root element
//        String testXml2 = XML.toString(new JSONArray(TestData.TEST_JSON2));
//        //System.out.println("converted XML: \n"+testXml2);
//        Object valueFromXml = MPathUtil.getValueFromXml(testXml2, mPath);
//        Assert.assertNotEquals(valueFromJson, valueFromXml);
//    }
//
//    @Test
//    public void FailCase4() {
//        String testJson = TestData.TEST_JSON3;
//        String mpath = "data.a";
//        Object val = MPathUtil.getValueFromJson(testJson, mpath);
//        //System.out.println(XML.toString(new JSONObject(testJson)));
//        Assert.assertNull(val);
//    }
//}

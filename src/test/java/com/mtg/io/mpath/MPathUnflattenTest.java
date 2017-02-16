/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.mpath;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class MPathUnflattenTest {

    String json1;

    @Before
    public void init() {
        json1 = "{\"a\":{\"b\":{\"c\":\"123\"}}}";
    }

    @Test
    public void TestCase1() {
        String mPath = "a.b.c";
        String value = "123";
        try {
            JSONObject json = MPathUtil.getJsonFromMPath(mPath, value);
            System.out.println(json);
        } catch (JSONException jx) {
            Assert.fail(jx.toString());
        }
    }

    @Test
    public void TestCase2() {
        try {
            JSONObject initJson = new JSONObject(json1);
            String mPath1 = "a.b.d", value1 = "456";
            JSONObject unflatJson1 = MPathUtil.appendJsonFromMPath(initJson, mPath1, value1);
            String mPath2 = "a.e.f", value2 = "8910";
            JSONObject unflatJson2 = MPathUtil.appendJsonFromMPath(unflatJson1, mPath2, value2);
            System.out.println(unflatJson2);
        } catch (JSONException jx) {
            Assert.fail(jx.toString());
        }
    }
}

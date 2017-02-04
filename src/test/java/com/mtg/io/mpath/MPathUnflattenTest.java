/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.mpath;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author anishhirlekar
 */
public class MPathUnflattenTest {
    
    @Test
    public void TestCase1(){
        String mPath = "a.b.c";
        String value = "123";
        try{
            JSONObject json = MPathUtil.getJsonFromMPath(mPath, value);
            System.out.println(json);
        }catch(JSONException jx){
            Assert.fail(jx.toString());
        }
    }
    
}

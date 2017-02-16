/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.io.mpath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

/**
 *
 * @author anishhirlekar
 */
public class MPathUnflattenTest {
    
    String TBL_MOVIE = "movie";
    String json1;
    Connection connection;
    
    @Before
    public void init() {
        json1 = "{\"a\":{\"b\":{\"c\":\"123\"}}}";
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtgdb","root", "");
        } catch (SQLException ex) {
            Assert.fail(ex.toString());
        }
    }
    
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
    
    @Test
    public void TestCase2(){
        try{
            JSONObject initJson = new JSONObject(json1);
            String mPath1 = "a.b.d", value1 = "456";
            JSONObject unflatJson1 = MPathUtil.appendJsonFromMPath(initJson, mPath1, value1);
            String mPath2 = "a.e.f", value2 = "8910";
            JSONObject unflatJson2 = MPathUtil.appendJsonFromMPath(unflatJson1, mPath2, value2);
            System.out.println(unflatJson2);
        }catch(JSONException jx){
            Assert.fail(jx.toString());
        }
    }
    
    @Test
    public void MysqlTest1(){
        try {
            String sql = "select id as 'a.b.c', name as 'a.b.d', rating from movie;";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            int c = 1;
            while(rs.next()){
                System.out.println("Row "+c+":");
                System.out.println(rs.getString("a.b.c"));
                System.out.println(rs.getString("a.b.d"));
                System.out.println(rs.getString("rating"));
                System.out.println();
                c++;
            }
        } catch (SQLException ex) {
            Assert.fail(ex.toString());
        }
    }
    
}

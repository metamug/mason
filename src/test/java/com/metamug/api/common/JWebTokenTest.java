/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.common;

import com.metamug.mason.common.JWebToken;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author user
 */
public class JWebTokenTest {

    public JWebTokenTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of HMACSHA256 method, of class JWebToken.
     */
    @org.junit.Test
    public void testWithData() {
        //generate JWT
        String token = new JWebToken("1234", "admin", LocalDate.now().plusDays(90).toEpochDay()).toString();
        //verify and use
        JWebToken incomingToken;
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience());
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }

    }

    @org.junit.Test
    public void testWithJson() {
        JSONObject payload = new JSONObject("{\"sub\":\"1234\",\"aud\":\"admin\","
                + "\"exp\":" + LocalDate.now().plusDays(90).toEpochDay() + "}");
        String token = new JWebToken(payload).toString();
        //verify and use
        JWebToken incomingToken;
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience());
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }
    }
    
    @org.junit.Test(expected=IllegalArgumentException.class)
    public void testBadHeaderFormat() {
        JSONObject payload = new JSONObject("{\"sub\":\"1234\",\"aud\":\"admin\","
                + "\"exp\":" + LocalDate.now().plusDays(90).toEpochDay() + "}");
        String token = new JWebToken(payload).toString();
        token = token.replaceAll("\\.","X");
        //verify and use
        JWebToken incomingToken;
        try {
            incomingToken = new JWebToken(token);
            if (incomingToken.isValid()) {
                Assert.assertEquals("1234", incomingToken.getSubject());
                Assert.assertEquals("admin", incomingToken.getAudience());                
            }
        } catch (NoSuchAlgorithmException ex) {
            fail("Invalid Token" + ex.getMessage());
        }
    }
    
    @org.junit.Test(expected=NoSuchAlgorithmException.class)
    public void testIncorrectHeader() throws NoSuchAlgorithmException {
        JSONObject payload = new JSONObject("{\"sub\":\"1234\",\"aud\":\"admin\","
                + "\"exp\":" + LocalDate.now().plusDays(90).toEpochDay() + "}");
        String token = new JWebToken(payload).toString();
        token = token.replaceAll("[^.]","X");
        //verify and use
        JWebToken incomingToken;
        
        incomingToken = new JWebToken(token);
        if (incomingToken.isValid()) {
            Assert.assertEquals("1234", incomingToken.getSubject());
            Assert.assertEquals("admin", incomingToken.getAudience());                
        }        
    }
}

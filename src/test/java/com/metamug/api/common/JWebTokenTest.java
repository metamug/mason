/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.common;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void test() {
        
        String token = new JWebToken("1234", "admin", LocalDate.now().plusDays(2).toEpochDay()).toString();      
        JWebToken incomingToken = null;
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

}

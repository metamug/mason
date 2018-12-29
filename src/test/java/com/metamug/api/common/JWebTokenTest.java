/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.common;

import java.util.Calendar;
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
    public void testHMACSHA256() {
        Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR, 2);
        String token = new JWebToken("1234","admin", c.getTime().getTime()).toString();
        System.out.println(token);
//        JWebToken incomingToken = new JWebToken(token)
//        if(token.isValid()){
//            
//        }
    }
    
}

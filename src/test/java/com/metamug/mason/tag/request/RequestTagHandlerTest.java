/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag.request;

import com.metamug.mason.RouterTest;
import com.metamug.mason.tag.request.RequestTagHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author user
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestTagHandlerTest{
    
    @Mock
    private PageContext context;
    
    
    public RequestTagHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doStartTag method, of class RequestTagHandler.
     */
    @Test
    public void testDoStartTag() throws Exception {
        System.out.println("doStartTag");
        RequestTagHandler instance = new RequestTagHandler();
        int expResult = 0;
        int result = instance.doStartTag();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doEndTag method, of class RequestTagHandler.
     */
    @Test
    public void testDoEndTag() throws Exception {
        System.out.println("doEndTag");
        RequestTagHandler instance = new RequestTagHandler();
        int expResult = 0;
        int result = instance.doEndTag();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

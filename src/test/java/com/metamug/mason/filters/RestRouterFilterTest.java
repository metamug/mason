/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author user
 */
public class RestRouterFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private StringWriter stringWriter;
    private PrintWriter writer;

    public RestRouterFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        
        //prepare String Writer
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException ex) {
            Logger.getLogger(RestRouterFilterTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRestCall() {
        when(request.getContentType()).thenReturn("blah");
        when(request.getServletPath()).thenReturn("/backend/resource");
        when(request.getMethod()).thenReturn("POST");
        RestRouterFilter router = new RestRouterFilter();

        try {
            router.doFilter(request, response, filterChain);
        } catch (IOException ex) {
            Logger.getLogger(RestRouterFilterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(RestRouterFilterTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        verify(request, atLeast(1)).getContentType(); // verify if Content Type was called
        verify(request, atLeast(1)).getServletPath(); // verify if servlet path was called
        writer.flush(); // it may not have been flushed yet...
        System.out.println(stringWriter.toString());
        assertTrue(stringWriter.toString().contains("415"));
    }
}

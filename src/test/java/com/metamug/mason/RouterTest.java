/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason;


import static com.metamug.mason.Router.HEADER_CONTENT_TYPE;
import static com.metamug.mason.entity.request.FormStrategy.APPLICATION_FORM_URLENCODED;
import static com.metamug.mason.entity.request.JsonStrategy.APPLICATION_JSON;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author user
 */
@RunWith(MockitoJUnitRunner.class)
public class RouterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ServletContext context = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(context);
        when(request.getServletContext().getContextPath()).thenReturn("backend");

        //prepare String Writer
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRestCall() {
        when(request.getServletPath()).thenReturn("/v1.9/resource");
        when(request.getMethod()).thenReturn("POST");
        String[] params = new String[]{"name"};
        when(request.getParameterNames()).thenReturn(Collections.enumeration(Arrays.asList(params)));
        when(request.getParameterValues("name")).thenReturn(new String[]{"anish", "deepak", "kaustubh"});
        when(request.getContentType()).thenReturn(APPLICATION_FORM_URLENCODED);
        Router router = new Router();

        try {
            router.doFilter(request, response, filterChain);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        verify(request, atLeast(1)).getContentType(); // verify if Content Type was called
        verify(request, atLeast(1)).getServletPath(); // verify if servlet path was called
        writer.flush(); // it may not have been flushed yet...
        //System.out.println(stringWriter.toString());
        assertTrue(stringWriter.toString().contains("404"));
    }

    @Test
    public void testJsonBody() {
        when(request.getHeader(HEADER_CONTENT_TYPE)).thenReturn(APPLICATION_JSON);
        when(request.getServletPath()).thenReturn("/v1.9/resource");
        when(request.getMethod()).thenReturn("POST");

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "kaustubh");
        ByteArrayInputStream bytestream = new ByteArrayInputStream(jsonBody.toString().getBytes(StandardCharsets.UTF_8));
        ServletInputStream stream = getServletInputStream(bytestream);
        try {
            when(request.getInputStream()).thenReturn(stream);
        } catch (IOException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        Router router = new Router();

        try {
            router.doFilter(request, response, filterChain);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        verify(request, atLeast(1)).getContentType(); // verify if Content Type was called
        verify(request, atLeast(1)).getServletPath(); // verify if servlet path was called
        writer.flush(); // it may not have been flushed yet...
        //System.out.println(stringWriter.toString());
        assertTrue(stringWriter.toString().contains("404"));
    }
    
    private ServletInputStream getServletInputStream(ByteArrayInputStream bytestream){
        return new ServletInputStream(){
            public int read() throws IOException {
              return bytestream.read();
            }

            @Override
            public boolean isFinished() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isReady() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setReadListener(ReadListener rl) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}

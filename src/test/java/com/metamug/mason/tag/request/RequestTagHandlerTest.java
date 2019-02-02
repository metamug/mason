/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag.request;

import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.tag.ResourceTagHandler;
import static com.metamug.mason.tag.ResourceTagHandler.HEADER_ACCEPT;
import com.metamug.mason.tag.RestTag;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author user
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestTagHandlerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private MasonRequest masonRequest;

    @Mock
    private PageContext context;

    @Mock
    private LinkedHashMap<String, Object> resultMap;

    @Mock
    private JspWriter writer;

    @InjectMocks
    RequestTagHandler requestTag = new RequestTagHandler();

    @InjectMocks
    ResourceTagHandler resourceTag = new ResourceTagHandler();

    public RequestTagHandlerTest() {

    }

    @Before
    public void setup() {

        String sampleObj = "{ \"name\":\"John\", \"age\":30, \"car\":null }";
        String sampleArray = "[\n"
                + "    { \"name\":\"Ford\", \"models\":[ \"Fiesta\", \"Focus\", \"Mustang\" ] },\n"
                + "    { \"name\":\"BMW\", \"models\":[ \"320\", \"X3\", \"X5\" ] },\n"
                + "    { \"name\":\"Fiat\", \"models\":[ \"500\", \"Panda\" ] }\n"
                + "  ]";
        String[] colNames = {"name", "age", "car"};
        resultMap = new LinkedHashMap<>();
        resultMap.put("res1", new JSONObject(sampleObj));
        resultMap.put("res2", new JSONArray(sampleArray));
        resultMap.put("res3", "Hello World");

        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);
        when(context.getOut()).thenReturn(writer);

    }

    /**
     * Test of doStartTag method, of class RequestTagHandler.
     */
    @Test
    public void requestTag() throws Exception {

        when(request.getHeader(HEADER_ACCEPT)).thenReturn("application/xml");
        when(request.getAttribute("mtgReq")).thenReturn(masonRequest);
        when(masonRequest.getMethod()).thenReturn("GET");

        requestTag.setMethod("GET");
        requestTag.setItem(false);
        assertEquals(1, requestTag.doStartTag());
        assertEquals(5, requestTag.doEndTag());

    }

    @Test
    public void resourceTag() throws Exception {

        assertEquals(1, resourceTag.doStartTag());
        assertEquals(5, resourceTag.doEndTag());

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag.request;

import com.metamug.mason.entity.request.MasonRequest;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @InjectMocks
    RequestTagHandler instance = new RequestTagHandler();

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

    }

    /**
     * Test of doStartTag method, of class RequestTagHandler.
     */
    @Test
    public void testDoStartTag() throws Exception {

        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);

        when(request.getAttribute("mtgReq")).thenReturn(masonRequest);
        when(masonRequest.getMethod()).thenReturn("GET");

        instance.setMethod("GET");
        instance.setItem(false);
        assertEquals(1, instance.doStartTag());
        assertEquals(5, instance.doEndTag());

    }

}

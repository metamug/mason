/*
 * Copyright (C) 2018 GAURI.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.metamug.mason.service.xrequest;

import com.metamug.mason.entity.xrequest.XResponse;
import com.metamug.mason.service.XRequestService;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author d3ep4k
 */
public class XRequestTest {

    private static final int STATUS_OK = 200;

    private final XRequestService xRequestService;

    public XRequestTest() {
        xRequestService = new XRequestService();
    }

    @Test
    public void testGetJSONWithQueryParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", XRequestService.APP_JSON);
        XResponse xr = xRequestService.get("https://postman-echo.com/get?foo1=bar1&foo2=bar2", headers, params);
        String foo = xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("args").getString("foo1");
        Assert.assertEquals("bar1", foo);
    }

    @Test
    public void testGetJSONWithParamsMap() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", XRequestService.APP_JSON);
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.get("https://postman-echo.com/get", headers, params);
        String foo = xr.getJsonForJsonXResponse().getJSONObject("body").getJSONObject("args").getString("foo1");
        Assert.assertEquals("bar1", foo);
    }

    @Test
    public void testPostWithFormParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_FORM_URLENCODED);
        headers.put("Accept", XRequestService.APP_JSON);
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.post("https://postman-echo.com/post", headers, params, null);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("form").getString("foo1"));
    }

    @Test
    public void testPostWithJSONParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_JSON);
        headers.put("Accept", XRequestService.APP_JSON);
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.post("https://postman-echo.com/post", headers, params, null);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("data").getString("foo1"));
    }

    @Test
    public void testPostWithJSONBody() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_JSON);
        headers.put("Accept", XRequestService.APP_JSON);
        JSONObject json = new JSONObject();
        json.put("foo1", "bar1");
        json.put("foo2", "bar2");
        XResponse xr = xRequestService.post("https://postman-echo.com/post", headers, params, json.toString());
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("data").getString("foo1"));
    }
    
    @Test
    public void testPutWithFormParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_FORM_URLENCODED);
        headers.put("Accept", XRequestService.APP_JSON);
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.put("https://postman-echo.com/put", headers, params, null);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("form").getString("foo1"));
    }

    @Test
    public void testPutWithJSONParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_JSON);
        headers.put("Accept", XRequestService.APP_JSON);
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.put("https://postman-echo.com/put", headers, params, null);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("data").getString("foo1"));
    }

    @Test
    public void testPutWithJSONBody() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", XRequestService.APP_JSON);
        headers.put("Accept", XRequestService.APP_JSON);
        JSONObject json = new JSONObject();
        json.put("foo1", "bar1");
        json.put("foo2", "bar2");
        XResponse xr = xRequestService.put("https://postman-echo.com/put", headers, params, json.toString());
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());

        Assert.assertEquals("bar1", xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("data").getString("foo1"));
    } 

    @Test
    public void testDeleteWithQueryParams() {
        Map<String, Object> params = new HashMap<>();
        XResponse xr = xRequestService.delete("https://postman-echo.com/delete?foo1=bar1&foo2=bar2", params);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
    }

    @Test
    public void testDeleteWithParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        XResponse xr = xRequestService.delete("https://postman-echo.com/delete", params);
        Assert.assertEquals(STATUS_OK, xr.getStatusCode());
    }
}

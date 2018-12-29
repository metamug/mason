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
package com.metamug.mason.services;

import com.metamug.mason.common.XResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author GAURI
 */
public class XRequestServiceTest {
//    XRequestService auth;

    public XRequestServiceTest() {
//        auth = new XRequestService();
    }

    @Test
    public void testGetXRequest() {
        Map<String, String> map = new HashMap<>();
        XResponse xr = XRequestService.get("https://postman-echo.com/get?foo1=bar1&foo2=bar2", map, map);
        String foo = xr.getJsonForJsonXResponse().getJSONObject("body")
                .getJSONObject("args").getString("foo1");
        Assert.assertEquals("bar1", foo);
    }

}

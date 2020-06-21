/*
 * Copyright 2020 pc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.metamug.mason;

import com.metamug.entity.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author pc
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private Request request;

    public HttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getMethod() {
        String method = super.getMethod();
        if (method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("put")) { //since JSP doesn't support DELETE and PUT
            return "POST";
        } else {
            return method;
        }
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

}

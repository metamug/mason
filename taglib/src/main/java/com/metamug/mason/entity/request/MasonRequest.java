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
package com.metamug.mason.entity.request;

import com.metamug.entity.Request;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Extend the implementation specific to mason for Request Object
 *
 * @author pc
 */
public class MasonRequest extends Request {

    private HttpServletRequest request;
    private RequestParamMap params; //hiding superclass field

    public MasonRequest(Request request) {
        super(request);
    }

    public MasonRequest(HttpServletRequest req) {
        this.request = req;
        this.params = new RequestParamMap(req);
    }

    /**
     * Use ${mtgReq.param.value} Do not need to convert parameters to a new map
     *
     * @param param
     * @return
     */
    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

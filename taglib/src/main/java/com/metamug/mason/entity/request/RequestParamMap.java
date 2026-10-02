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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * HashMap Wrapper to make request parameters accessible with ${mtgReq.params["name"]}
 * @author pc
 */
public class RequestParamMap extends HashMap<String, String> {

    protected HttpServletRequest request;

    public RequestParamMap(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String get(Object key) {
        return request.getParameter((String) key);
    }

    /**
     * get() reads straight from the servlet request, so the map itself stays empty. Everything that enumerates
     * the map (entrySet, keySet, size, ...) therefore has to be answered from the request as well, otherwise code
     * that iterates the parameters (e.g. the Groovy runner binding one variable per parameter) sees nothing.
     * Values put into the map (extra parameters) are included.
     */
    private Map<String, String> view() {
        Map<String, String> all = new LinkedHashMap<>(super.size() == 0 ? new HashMap<String, String>() : new HashMap<>(this));
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            all.put(e.getKey(), e.getValue() != null && e.getValue().length > 0 ? e.getValue()[0] : null);
        }
        return all;
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return view().entrySet();
    }

    @Override
    public Set<String> keySet() {
        return view().keySet();
    }

    @Override
    public Collection<String> values() {
        return view().values();
    }

    @Override
    public int size() {
        return view().size();
    }

    @Override
    public boolean isEmpty() {
        return view().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return request.getParameter((String) key) != null || super.containsKey(key);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author anishhirlekar
 */
public class QueryManagerService {

    private final InputStream queryFis;

    public QueryManagerService(InputStream propFileInputStream) {
        queryFis = propFileInputStream;
    }

    public Map<String, String> getQueryMap() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Properties p = new Properties();
        p.load(queryFis);

        p.stringPropertyNames().forEach(key -> {
            map.put(key, p.getProperty(key));
        });

        return map;
    }
}

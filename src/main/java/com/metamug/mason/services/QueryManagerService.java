/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author anishhirlekar
 */
public class QueryManagerService {

    private final String queryFile = "query.properties";
    private final InputStream queryFis;

    public QueryManagerService() {
        queryFis = QueryManagerService.class.getClassLoader().getResourceAsStream(queryFile);
    }

    public HashMap<String, String> getQueryMap() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Properties p = new Properties();
        p.load(queryFis);

        p.stringPropertyNames().forEach((key) -> {
            map.put(key, p.getProperty(key));
        });

        return map;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author anishhirlekar
 */
public class QueryManagerServiceTest {

    private QueryManagerService qmService;
    private static final String queryFile = "query.properties";

    public QueryManagerServiceTest() {
        qmService = new QueryManagerService(QueryManagerServiceTest.class.getClassLoader().getResourceAsStream(queryFile));
    }

    @Test
    public void testFileRead() {
        try {
            Map<String, String> queryMap = qmService.getQueryMap();
            String qry1 = "SELECT * from customer";
            String qry2 = "UPDATE customer SET name=?";
            Assert.assertEquals(qry1, queryMap.get("qry1"));
            Assert.assertEquals(qry2, queryMap.get("qry2"));
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
    }
}

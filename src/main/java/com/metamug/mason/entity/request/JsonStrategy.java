/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.request;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author D3ep4k
 */
public class JsonStrategy extends ParamExtractStrategy {

    /**
     *
     * @param request
     */
    public JsonStrategy(HttpServletRequest request) {
        super(request);

        String line;
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonStrategy.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        Map<String, Object> flattenAsMap = JsonFlattener.flattenAsMap(jsonData.toString());
        flattenAsMap.entrySet().forEach(entry -> {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            addKeyPair(masonRequest, new String[]{key, value}, params);
        });
        //Add jsonData as String
        params.put("mtgRawJson", jsonData.toString());
    }
}

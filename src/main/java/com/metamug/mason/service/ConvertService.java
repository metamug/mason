/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ConvertService {

    public void convertToMap(Object value, LinkedHashMap<String, Object> targetMap, String propName) {
        if (value instanceof ResultImpl) {
            convertResultToMap((ResultImpl) value, targetMap, propName);
        } else if (value instanceof JSONObject) {
            convertJsonObjectToMap((JSONObject) value, targetMap, propName);
        } else if (value instanceof String) {
            targetMap.put(propName, (String) value);
        }
    }

    private void convertResultToMap(ResultImpl resultImpl, LinkedHashMap<String, Object> map, String propName) {
        SortedMap[] rows = resultImpl.getRows();
        String[] columnNames = resultImpl.getColumnNames();

        if (rows.length > 0) {
            for (SortedMap row : rows) {
                for (int i = 0; i < columnNames.length; i++) {
                    String columnName = columnNames[i].isEmpty() || columnNames[i].equalsIgnoreCase("null") ? "col" + i : columnNames[i];
                    String rowValue = String.valueOf(row.get(columnName));

                    if (rowValue != null && !rowValue.trim().isEmpty() && !rowValue.trim().equalsIgnoreCase("null")) {
                        map.put(propName + "." + columnName, String.valueOf((row.get(columnName))));
                    }
                }
            }
        }
    }

    private void convertJsonObjectToMap(JSONObject jsonObject, LinkedHashMap<String, Object> map, String propName) {
        Map<String, Object> flatMap = JsonFlattener.flattenAsMap(jsonObject.toString());
        flatMap.entrySet().forEach(entry -> {
            map.put(propName + "." + entry.getKey(), entry.getValue().toString());
        });
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.service;

import java.util.LinkedHashMap;
import java.util.SortedMap;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

/**
 *
 * @author anishhirlekar
 */
public class ConvertService {
    public void convertResultToMap(ResultImpl resultImpl, LinkedHashMap<String,Object> map){
        SortedMap[] rows = resultImpl.getRows();
        String[] columnNames = resultImpl.getColumnNames();
        if (rows.length > 0) {
            for (SortedMap row : rows) {
                for (int i = 0; i < columnNames.length; i++) {
                    String columnName = columnNames[i].isEmpty() || columnNames[i].equalsIgnoreCase("null") ? "col" + i : columnNames[i];
                    String rowValue = String.valueOf(row.get(columnName));
                    if (rowValue != null && !rowValue.trim().isEmpty() && !rowValue.trim().equalsIgnoreCase("null")) 
                        map.put(columnName, String.valueOf((row.get(columnName))));                 
                }
            }
        }
    }
}

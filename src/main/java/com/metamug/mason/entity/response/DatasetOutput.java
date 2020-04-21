/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.response;

import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.SortedMap;

/**
 * Dataset JSON output object
 */
public class DatasetOutput extends JSONOutput {

    public static final String COLUMNS = "columns";
    public static final String DATASET = "dataset";

    @Override
    protected Object getJson(ResultImpl impl) {
        return resultSetToDataSet(impl);
    }

    private JSONObject resultSetToDataSet(ResultImpl resultImpl) {
        SortedMap[] rows = resultImpl.getRows();
        String[] columnNames = resultImpl.getColumnNames();
        JSONObject object = new JSONObject();
        JSONArray columnArray = new JSONArray();
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i].isEmpty() || columnNames[i].equalsIgnoreCase("null") ? "col" + i : columnNames[i];
            columnArray.put(columnName);
        }
        object.put(COLUMNS, columnArray);
        JSONArray dataSetArray = new JSONArray();
        for (SortedMap row : rows) {
            JSONArray rowArray = new JSONArray();
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].isEmpty() || columnNames[i].equalsIgnoreCase("null") ? "col" + i : columnNames[i];
                rowArray.put((row.get(columnName) != null) ? row.get(columnName) : "null");
            }
            dataSetArray.put(rowArray);
        }
        object.put(DATASET, dataSetArray);
        return object;
    }

    @Override
    public String getContentType() {
        return HEADER_DATASET;
    }
}

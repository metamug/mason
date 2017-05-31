/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import com.mtg.io.mpath.MPathUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author deepak
 */
public class OutputTagHandler extends BodyTagSupport {

    private LinkedHashMap value;
    private String type;
    private String tableName;

    /**
     * Called by the container to invoke this tag. The implementation of this method is provided by the tag library developer, and handles all tag processing, body iteration, etc.
     *
     * @return
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        LinkedHashMap<String, Object> mtgResultMap = (LinkedHashMap) value;
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        String header = (String) type;
        int mapSize = mtgResultMap.size();
        boolean emptyContent = true;
        int contentLength = 0;
        if (header != null && Arrays.asList(header.split("/")).contains("xml")) {
            response.setContentType("application/xml");
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response>");
            for (Map.Entry<String, Object> entry : mtgResultMap.entrySet()) {
                ResultImpl resultImpl = (ResultImpl) entry.getValue();
                SortedMap[] rows = resultImpl.getRows();
                String[] columnNames = resultImpl.getColumnNames();
                if (rows.length > 0 && emptyContent) {
                    emptyContent = false;
                }
                for (SortedMap row : rows) {
                    xmlBuilder.append("<").append(tableName.replaceAll(" ", "_")).append(">");
                    for (String columnName : columnNames) {
                        xmlBuilder.append("<").append(columnName.replaceAll(" ", "_")).append(">").append(((row.get(columnName) == null) ? "null" : row.get(columnName))).append("</").append(columnName.replaceAll(" ", "_")).append(">");
                    }
                    xmlBuilder.append("</").append(tableName.replaceAll(" ", "_")).append(">");
                }
            }
            xmlBuilder.append("</response>");
            contentLength = xmlBuilder.toString().length();
            try {
                if (emptyContent) {
                    response.setStatus(204);
                } else {
                    pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                    out.print(xmlBuilder.toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(OutputTagHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setContentType("application/json");
            JSONObject responseJson = new JSONObject(new LinkedHashMap<>());
            for (Map.Entry<String, Object> entry : mtgResultMap.entrySet()) {
                try {
                    ResultImpl resultImpl = (ResultImpl) entry.getValue();
                    SortedMap[] rows = resultImpl.getRows();
                    String[] columnNames = resultImpl.getColumnNames();
                    if (rows.length > 0 && emptyContent) {
                        emptyContent = false;
                    }
                    if (mapSize == 1) {
                        try {
                            if (emptyContent) {
                                response.setStatus(204);
                            } else {
                                JSONArray array = new JSONArray();
                                for (SortedMap<String, Object> row : rows) {
                                    JSONObject rowJson = new JSONObject();
                                    for (String columnName : columnNames) {

                                        rowJson = MPathUtil.appendJsonFromMPath(rowJson, columnName, (row.get(columnName) != null) ? row.get(columnName) : "null");
                                    }
                                    array.put(rowJson);
                                }
                                pageContext.setAttribute("Content-Length", array.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(array.toString());
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(OutputTagHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JSONArray array = new JSONArray();
                        for (SortedMap row : rows) {
                            JSONObject rowJson = new JSONObject(new LinkedHashMap<>());
                            for (String columnName : columnNames) {
                                rowJson = MPathUtil.appendJsonFromMPath(rowJson, columnName, (row.get(columnName) != null) ? row.get(columnName) : "null");
                            }
                            array.put(row);
                        }
                        contentLength += array.toString().length();
                        System.out.println(array.toString());
                        responseJson.append("response", array);
                    }
                } catch (ClassCastException ex) {
                    int resultUpdated = (int) entry.getValue();
                    responseJson.append("response", resultUpdated + " results updated");
                }
            }
            try {
                if (emptyContent) {
                    response.setStatus(204);
                } else if (mapSize > 1) {
                    pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                    out.write(responseJson.get("response").toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(OutputTagHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return EVAL_PAGE;
    }

    public void setValue(LinkedHashMap resultMap) {
        this.value = resultMap;
    }

    public void setType(String value) {
        this.type = value;
    }

    public void setTableName(String value) {
        this.tableName = value;
    }

//    @Override
//    public void doCatch(Throwable throwable) throws Throwable {
//        throw throwable;
//    }
//
//    @Override
//    public void doFinally() {
////        value = null;
////        type = null;
//        tableName = null;
//    }
}

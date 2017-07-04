/** ***********************************************************************
 * Freeware Licence Agreement
 *
 * This licence agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENCE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENCE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENCE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this licence, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 * 1. Licence
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This licence is effective until terminated. The Licence will terminate automatically without notice from METAMUG if you fail to comply with any provision of this Licence. Upon termination you must destroy the Software and all copies thereof. You may terminate this Licence at any time by destroying the Software and all copies thereof. Upon termination of this licence for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharastra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharastra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
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
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author deepak
 */
public class OutputTagHandler extends BodyTagSupport {
    
    public static String KEY_COLUMN = "columns";
    public static String KEY_DATASET = "dataset";

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
        int resultCounter = 0;
        boolean emptyContent = true;
        int contentLength = 0;
        //Accept: application/xml
        if (header != null && Arrays.asList(header.split("/")).contains("xml")) {
            //System.out.println("Mapsize: "+mapSize);
            response.setContentType("application/xml");
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            xmlBuilder.append("<response>");
            for (Map.Entry<String, Object> entry : mtgResultMap.entrySet()) {
                Object mapValue = entry.getValue();
                if (mapValue instanceof ResultImpl) {
                    ResultImpl resultImpl = (ResultImpl) mapValue;
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
                                for (SortedMap row : rows) {
                                    xmlBuilder.append("<").append(tableName.replaceAll(" ", "_")).append(">");
                                    for (String columnName : columnNames) {
                                        xmlBuilder.append("<").append(columnName.replaceAll(" ", "_")).append(">").append(((row.get(columnName) == null) ? "null" : row.get(columnName))).append("</").append(columnName.replaceAll(" ", "_")).append(">");
                                    }
                                    xmlBuilder.append("</").append(tableName.replaceAll(" ", "_")).append(">");
                                }
                                xmlBuilder.append("</response>");
                            }
                            contentLength = xmlBuilder.toString().length();
                            pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                            out.print(xmlBuilder.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    } else {
                        for (SortedMap row : rows) {
                            xmlBuilder.append("<").append(tableName.replaceAll(" ", "_")).append(">");
                            for (String columnName : columnNames) {
                                xmlBuilder.append("<").append(columnName.replaceAll(" ", "_")).append(">").append(((row.get(columnName) == null) ? "null" : row.get(columnName))).append("</").append(columnName.replaceAll(" ", "_")).append(">");
                            }
                            xmlBuilder.append("</").append(tableName.replaceAll(" ", "_")).append(">");
                        }
                    }
                    contentLength += xmlBuilder.toString().length();
                } else if (mapValue instanceof String) {
                    String result = (String) mapValue;
                    // Print result of Code execution
                    if (!result.isEmpty() && emptyContent) {
                        emptyContent = false;
                    }
                    if (mapSize == 1) {
                        if (emptyContent) {
                            response.setStatus(204);
                        } else {
                            try {
                                int temp = (++resultCounter);
                                if (entry.getKey().contains("error")) {
                                    xmlBuilder.append("<error").append(temp).append(">").append(result).append("</error").append(temp).append(">");
                                } else {
                                    xmlBuilder.append("<result").append(temp).append(">").append(result).append("</result").append(temp).append(">");
                                }
                                pageContext.setAttribute("Content-Length", xmlBuilder.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(xmlBuilder.toString());
                            } catch (IOException ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            }
                        }
                    } else {
                        int temp = (++resultCounter);
                        if (entry.getKey().contains("error")) {
                            xmlBuilder.append("<error").append(temp).append(">").append(result).append("</error").append(temp).append(">");
                        } else {
                            xmlBuilder.append("<result").append(temp).append(">").append(result).append("</result").append(temp).append(">");
                        }
                        contentLength += xmlBuilder.toString().length();
                    }
                } else {
                    emptyContent = false;
                    Object result = mapValue;
                    // Print result of Code execution
                    if (mapSize == 1) {
                        if (emptyContent) {
                            response.setStatus(204);
                        } else {
                            try {
                                int temp = (++resultCounter);
                                if (entry.getKey().contains("error")) {
                                    xmlBuilder.append("<error").append(temp).append(">").append(result).append("</error").append(temp).append(">");
                                } else {
                                    xmlBuilder.append("<result").append(temp).append(">").append(result).append("</result").append(temp).append(">");
                                }
                                pageContext.setAttribute("Content-Length", xmlBuilder.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(xmlBuilder.toString());
                            } catch (IOException ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            }
                        }
                    } else {
                        int temp = (++resultCounter);
                        if (entry.getKey().contains("error")) {
                            xmlBuilder.append("<error").append(temp).append(">").append(result).append("</error").append(temp).append(">");
                        } else {
                            xmlBuilder.append("<result").append(temp).append(">").append(result).append("</result").append(temp).append(">");
                        }
                        contentLength += xmlBuilder.toString().length();
                    }
                }
            }
            try {
                if (emptyContent) {
                    response.setStatus(204);
                } else {
                    xmlBuilder.append("</response>");
                    pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                    out.print(xmlBuilder.toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
        //Accept: application/json+dataset
        else if(header != null && Arrays.asList(header.split("/")).contains("json+dataset")) {
            response.setContentType("application/json+dataset");
            JSONObject responseJson = new JSONObject(new LinkedHashMap<>());
            for (Map.Entry<String, Object> entry : mtgResultMap.entrySet()) {
                Object mapValue = entry.getValue();
                if (mapValue instanceof ResultImpl) {
                    //SQL Resultset
                    ResultImpl resultImpl = (ResultImpl) mapValue;
                    SortedMap[] rows = resultImpl.getRows();
                    String[] columnNames = resultImpl.getColumnNames();
                    if (rows.length > 0 && emptyContent) {
                        emptyContent = false;
                    }  
                    //Single Sql Tag
                    if (mapSize == 1) {
                        try {
                            if (emptyContent) {
                                response.setStatus(204);
                            } else {           
                                JSONObject object = new JSONObject();
                                JSONArray columnArray = new JSONArray();
                                for (String columnName : columnNames) {
                                    columnArray.put(columnName);
                                }
                                object.put(KEY_COLUMN, columnArray);
                                JSONArray dataSetArray = new JSONArray();                                
                                for (SortedMap<String, Object> row : rows) {
                                    JSONArray rowArray = new JSONArray();
                                    for (String columnName : columnNames) {
                                        rowArray.put((row.get(columnName) != null) ? row.get(columnName) : "null");
                                    }
                                    dataSetArray.put(rowArray);
                                }
                                object.put(KEY_DATASET, dataSetArray);
                                pageContext.setAttribute("Content-Length", object.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(object.toString());
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    } 
                    //Multiple tags
                    else {
                        JSONObject object = new JSONObject();
                        JSONArray columnArray = new JSONArray();
                        for (String columnName : columnNames) {
                            columnArray.put(columnName);
                        }
                        object.put(KEY_COLUMN, columnArray);
                        JSONArray dataSetArray = new JSONArray();
                        for (SortedMap row : rows) {
                            JSONArray rowArray = new JSONArray();
                            for (String columnName : columnNames) {
                                rowArray.put((row.get(columnName) != null) ? row.get(columnName) : "null");
                            }
                            dataSetArray.put(rowArray);
                        }
                        object.put(KEY_DATASET, dataSetArray);
                        contentLength += object.toString().length();
                        responseJson.append("response", object);
                    }                    
                } else if(mapValue instanceof String) {
                    String result = (String) mapValue;
                    // Print result of Code execution
                    if (!result.isEmpty() && emptyContent) {
                        emptyContent = false;
                    }
                    if (mapSize == 1) {
                        if (emptyContent) {
                            response.setStatus(204);
                        } else {
                            try {
                                JSONObject codeResult = new JSONObject();
                                if (entry.getKey().contains("error")) {
                                    codeResult.put("error" + (++resultCounter), result);
                                } else {
                                    codeResult.put("result" + (++resultCounter), result);
                                }
                                pageContext.setAttribute("Content-Length", codeResult.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(codeResult.toString());
                            } catch (IOException ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            }
                        }
                    } else {
                        JSONArray array = new JSONArray();
                        JSONObject codeResult = new JSONObject();
                        if(entry.getKey().contains("error")) {
                            codeResult.put("error" + (++resultCounter), result);
                        } else {
                            codeResult.put("result" + (++resultCounter), result);
                        }
                        array.put(codeResult);
                        contentLength += array.toString().length();
                        responseJson.append("response", array);
                    }
                } else {
                    Object result = mapValue;
                    emptyContent = false;
                    // Print result of Code execution
                    if (mapSize == 1) {
                        try {
                            JSONObject codeResult = new JSONObject();
                            if (entry.getKey().contains("error")) {
                                codeResult.put("error" + (++resultCounter), result);
                            } else {
                                codeResult.put("result" + (++resultCounter), result);
                            }
                            pageContext.setAttribute("Content-Length", codeResult.toString().length(), PageContext.REQUEST_SCOPE);
                            out.print(codeResult.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    } else {
                        JSONArray array = new JSONArray();
                        JSONObject codeResult = new JSONObject();
                        if (entry.getKey().contains("error")) {
                            codeResult.put("error" + (++resultCounter), result);
                        } else {
                            codeResult.put("result" + (++resultCounter), result);
                        }
                        array.put(codeResult);
                        contentLength += array.toString().length();
                        responseJson.append("response", array);
                    }
                }
            }
            try {
                if (emptyContent) {
                    response.setStatus(204);
                } else if (mapSize > 1) {
                    pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                    out.print(responseJson.get("response").toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
        //Accept: application/json OR default
        else {
            response.setContentType("application/json");
            JSONObject responseJson = new JSONObject(new LinkedHashMap<>());
            for (Map.Entry<String, Object> entry : mtgResultMap.entrySet()) {
                Object mapValue = entry.getValue();
                if (mapValue instanceof ResultImpl) {
                    //SQL Resultset
                    ResultImpl resultImpl = (ResultImpl) mapValue;
                    SortedMap[] rows = resultImpl.getRows();
                    String[] columnNames = resultImpl.getColumnNames();
                    if (rows.length > 0 && emptyContent) {
                        emptyContent = false;
                    }
                    //Single Sql Tag
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
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
                    //Multiple tags
                    else {
                        JSONArray array = new JSONArray();
                        for (SortedMap row : rows) {
                            JSONObject rowJson = new JSONObject(new LinkedHashMap<>());
                            for (String columnName : columnNames) {
                                rowJson = MPathUtil.appendJsonFromMPath(rowJson, columnName, (row.get(columnName) != null) ? row.get(columnName) : "null");
                            }
                            array.put(rowJson);
                        }
                        contentLength += array.toString().length();
                        responseJson.append("response", array);
                    }
                } else if (mapValue instanceof String) {
                    String result = (String) mapValue;
                    // Print result of Code execution
                    if (!result.isEmpty() && emptyContent) {
                        emptyContent = false;
                    }
                    if (mapSize == 1) {
                        if (emptyContent) {
                            response.setStatus(204);
                        } else {
                            try {
                                JSONObject codeResult = new JSONObject();
                                if (entry.getKey().contains("error")) {
                                    codeResult.put("error" + (++resultCounter), result);
                                } else {
                                    codeResult.put("result" + (++resultCounter), result);
                                }
                                pageContext.setAttribute("Content-Length", codeResult.toString().length(), PageContext.REQUEST_SCOPE);
                                out.print(codeResult.toString());
                            } catch (IOException ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            }
                        }
                    } else {
                        JSONArray array = new JSONArray();
                        JSONObject codeResult = new JSONObject();
                        if (entry.getKey().contains("error")) {
                            codeResult.put("error" + (++resultCounter), result);
                        } else {
                            codeResult.put("result" + (++resultCounter), result);
                        }
                        array.put(codeResult);
                        contentLength += array.toString().length();
                        responseJson.append("response", array);
                    }
                } else {
                    Object result = mapValue;
                    emptyContent = false;
                    // Print result of Code execution
                    System.out.println("Mapsize: "+mapSize);
                    if (mapSize == 1) {
                        try {
                            JSONObject codeResult = new JSONObject();
                            if (entry.getKey().contains("error")) {
                                codeResult.put("error" + (++resultCounter), result);
                            } else {
                                //codeResult.put("result" + (++resultCounter), result);
                                codeResult = new JSONObject(result);
                            }
                            System.out.println("result: "+result);                            
                            System.out.println("code_result: "+codeResult);
                            pageContext.setAttribute("Content-Length", codeResult.toString().length(), PageContext.REQUEST_SCOPE);
                            out.print(codeResult.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    } else {
                        JSONArray array = new JSONArray();
                        JSONObject codeResult = new JSONObject();
                        if (entry.getKey().contains("error")) {
                            codeResult.put("error" + (++resultCounter), result);
                        } else {
                            codeResult.put("result" + (++resultCounter), result);
                        }
                        array.put(codeResult);
                        contentLength += array.toString().length();
                        responseJson.append("response", array);
                    }
                }
            }
            try {
                if (emptyContent) {
                    response.setStatus(204);
                } else if (mapSize > 1) {
                    pageContext.setAttribute("Content-Length", contentLength, PageContext.REQUEST_SCOPE);
                    out.print(responseJson.get("response").toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
}

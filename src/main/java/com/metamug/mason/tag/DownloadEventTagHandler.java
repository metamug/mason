/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.tag;

import com.metamug.entity.Request;
import com.metamug.event.DownloadEvent;
import com.metamug.event.DownloadListener;
import com.metamug.mason.entity.request.MasonRequest;
import com.metamug.mason.exception.MetamugError;
import com.metamug.mason.exception.MetamugException;
import com.metamug.mason.service.ConnectionProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.sql.DataSource;

/**
 *
 * @author anishhirlekar
 */
public class DownloadEventTagHandler extends RestTag {
    
    private DataSource ds;
    
    public static final String TYPE_OCTET_STREAM = "application/octet-stream";
    
    @Override
    public int doEndTag() throws JspException {
        ds = ConnectionProvider.getMasonDatasource();
        String accept = request.getHeader("Accept");
    
        if(accept.contains(TYPE_OCTET_STREAM)) {
            String listenerClass;
            Properties prop = new Properties();
            try (InputStream fis = UploadEventTagHandler.class.getClassLoader().getResourceAsStream("config.properties")) {
                prop.load(fis);
                listenerClass = prop.getProperty("DownloadListener");
                
                if (listenerClass != null) {
                    InputStream inputStream = callDownloadEvent(listenerClass,request);
                    try (OutputStream out = response.getOutputStream()) {
                        response.setContentType(TYPE_OCTET_STREAM);
                        //response.setContentLength((int) fileLength);
                        //response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

                        int i;
                        while ((i = inputStream.read()) != -1) {
                            out.write(i);
                        }
                        out.flush();                        
                    }
                }
            } catch (ClassNotFoundException ex) {
                throw new JspException("", new MetamugException(MetamugError.NO_DOWNLOAD_LISTENER));
            } catch (NullPointerException | IOException | ServletException | InstantiationException | IllegalAccessException ex) {
                throw new JspException("", new MetamugException(MetamugError.DOWNLOAD_CODE_ERROR, ex));
            } catch (RuntimeException ex) {
                throw new JspException("", new MetamugException(MetamugError.DOWNLOAD_CODE_ERROR, ex));
            } catch (Exception ex) {
                throw new JspException("", new MetamugException(MetamugError.DOWNLOAD_CODE_ERROR, ex));
            }
            
            return SKIP_PAGE;
        }
        
        return EVAL_PAGE;
    }
    
    private InputStream callDownloadEvent(String listenerClass, HttpServletRequest req) throws NullPointerException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, RuntimeException, Exception {
        if (listenerClass != null) {
            Class cls = Class.forName((String) listenerClass);
            Object newInstance = cls.newInstance();
            DownloadListener listener;
            MasonRequest mtg = (MasonRequest) req.getAttribute("mtgReq");
            if (DownloadListener.class.isAssignableFrom(cls)) {
                listener = (DownloadListener) newInstance;
                //Add Request Header values
                Map<String, String> reqHeaders = new HashMap<>();
                Enumeration<String> headerNames = req.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String header = headerNames.nextElement();
                    if (req.getHeader(header) != null) {
                        reqHeaders.put(header, req.getHeader(header));
                    }
                }
                //Add Request Parameter values
                Map<String, String> reqParams = new HashMap<>();
                Enumeration<String> parameterNames = req.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String param = parameterNames.nextElement();
                    if (req.getParameter(param) != null) {
                        reqParams.put(param, req.getParameter(param));
                    }
                }
                
                    String resourceURI = (String) req.getAttribute("javax.servlet.forward.request_uri");
                    String name = null, parent = null;
                    float version = 1.0f;
                    String[] tokens = resourceURI.split("/");
                    //{appName}/v{versionNumber}/{resourceName}/{id}
                    if (tokens.length >= 4) {
                        try {
                            version = Float.valueOf(tokens[2].replaceAll("v", ""));
                        } catch (NumberFormatException ex) {
                            version = 1.0f;
                        }
                        name = tokens[3];
                    }
                    //{appName}/v{versionNumber}/{parent}/{parentId}/{child}/{id}
                    if (tokens.length >= 6) {
                        parent = tokens[3];
                        name = tokens[5];
                    }
                    Request downloadRequest = new Request(reqParams, reqHeaders, "GET", new com.metamug.entity.Resource(name, version, resourceURI, parent));
                  
                    InputStream inputStream = listener.onDownloadRequest(new DownloadEvent(downloadRequest), ds);
                    //Sync params to HttpRequest params
                    reqParams.entrySet().forEach((entry) -> {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        mtg.getParams().put(key, value);
                    });
                    return inputStream;
                
            } else {
                throw new JspException("", new MetamugException(MetamugError.CLASS_NOT_IMPLEMENTED, "Class " + cls + " isn't a DownloadListener."));
            }
        } else {
            throw new JspTagException("No implementation of DownloadListener was found.", new MetamugException(MetamugError.NO_DOWNLOAD_LISTENER));
        }
    }
}

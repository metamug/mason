/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.filter;

import com.metamug.api.common.MtgRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
@MultipartConfig
public class RestRouterFilter implements Filter {

    private static final boolean DEBUG = false;
    private FilterConfig filterConfig = null;

    public RestRouterFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();
        String[] tokens = path.split("/");
        System.out.println(tokens.length);
        if (tokens.length > 2) {
            if (req.getServletPath().contains("index") || req.getServletPath().contains("docs") || req.getServletPath().contains("code")) {
                chain.doFilter(request, response);
            } else {
                StringBuilder domain = new StringBuilder();
                domain.append(req.getScheme()).append("://").append(req.getServerName());
                if (req.getServerPort() != 80 && req.getServerPort() != 443) {
                    domain.append(":").append(req.getServerPort());
                }
//                System.out.println("local domain:" + domain.toString());
                response.setContentType("application/json");
                String contentType = req.getContentType() == null ? "" : req.getContentType();
                System.out.println(contentType);
                if (req.getMethod().equalsIgnoreCase("get") || req.getMethod().equalsIgnoreCase("delete") || (contentType != null && (contentType.equalsIgnoreCase("application/json") || contentType.equalsIgnoreCase("application/xml") || contentType.contains("html") || contentType.contains("application/x-www-form-urlencoded") || contentType.contains("multipart/form-data")))) {
                    try {
                        MtgRequest mtgReq = createMtgResource(tokens, req.getMethod(), req);
                        req.setAttribute("mtgReq", mtgReq);
                        String appName = req.getServletContext().getContextPath();
                        String version = tokens[1];
                        String resourceName = "";
                        if (tokens.length == 5 || tokens.length == 6) {
                            resourceName = tokens[4];
                        } else {
                            resourceName = tokens[2];
                        }
                        if (new File(System.getProperty("catalina.base") + File.separator + "api/" + appName + "/WEB-INF/resources/" + version.toLowerCase() + "/" + resourceName + ".jsp").exists()) {
                            req.getRequestDispatcher("/WEB-INF/resources/" + version.toLowerCase() + "/" + resourceName + ".jsp").forward(new HttpServletRequestWrapper(req) {
                                @Override
                                public String getMethod() {
                                    String method = super.getMethod();
                                    if (method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("put")) {
                                        return "POST";
                                    } else {
                                        return method;
                                    }
                                }
                            }, response);
                        } else {
                            try (ServletOutputStream writer = response.getOutputStream()) {
                                response.setContentType("application/json;charset=UTF-8");
                                response.setCharacterEncoding("UTF-8");
                                res.setStatus(404);
                                JSONObject obj = new JSONObject();
                                obj.put("status", 404);
                                obj.put("message", "Resource doesn't exist");
                                writer.print(obj.toString());
                                writer.flush();
                            }
                        }
                    } catch (IOException | ServletException | JSONException ex) {
                        try (ServletOutputStream writer = response.getOutputStream()) {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            res.setStatus(422);
                            JSONObject obj = new JSONObject();
                            obj.put("status", 422);
                            if (ex.getCause() != null) {
                                String cause = ex.getCause().toString();
                                System.out.println("router cause:" + cause);
                                obj.put("message", cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                            }
                            if (ex.getMessage().contains("ELException")) {
                                obj.put("message", "Incorrect test condition in '" + tokens[2] + "' resource");
                                obj.put("status", 500);
                                res.setStatus(500);
                            } else {
                                System.out.println("router exception:" + ex.getMessage());
                                obj.put("message", ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                            }
//                            System.out.println("router trace:" + Arrays.toString(ex.getStackTrace()));
                            writer.print(obj.toString());
                            writer.flush();
                        }
                    }
                } else {
                    try (ServletOutputStream writer = response.getOutputStream()) {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        res.setStatus(415);
                        JSONObject obj = new JSONObject();
                        obj.put("status", 415);
                        obj.put("message", "Unsupported Media Type");
                        writer.print(obj.toString());
                        writer.flush();
                    }
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private MtgRequest createMtgResource(String[] tokens, String method, HttpServletRequest request) throws IOException, JSONException, ServletException {
        MtgRequest mtgRequest = new MtgRequest();
        //Set parent value and pid
        if (tokens.length == 5 || tokens.length == 6) {
            System.out.println("setting parent:" + tokens[2]);
            mtgRequest.setParent(tokens[2]);
            System.out.println("setting parentID:" + tokens[3]);
            mtgRequest.setPid(tokens[3]);
            mtgRequest.setId((tokens.length > 5) ? tokens[5] : null);
        } else {
            mtgRequest.setId((tokens.length > 3) ? tokens[3] : null);
        }
        mtgRequest.setMethod(method);
        mtgRequest.setUri(tokens[2]);
        Map<String, String> params = new HashMap<>();
        String contentType = request.getHeader("Content-Type") == null ? "" : request.getHeader("Content-Type");
        if (method.equalsIgnoreCase("get") || method.equalsIgnoreCase("delete") || contentType.contains("application/html") || contentType.contains("application/x-www-form-urlencoded")) {
            if (!method.equalsIgnoreCase("PUT")) {
                Enumeration<String> parameters = request.getParameterNames();
                while (parameters.hasMoreElements()) {
                    String paramName = parameters.nextElement();
                    if (paramName.trim().equalsIgnoreCase("id")) {
                        mtgRequest.setId(request.getParameter(paramName).trim());
                    } else if (paramName.trim().equalsIgnoreCase("pid")) {
                        mtgRequest.setPid(request.getParameter(paramName).trim());
                    } else if (paramName.trim().equalsIgnoreCase("uid")) {
                        mtgRequest.setUid(request.getParameter(paramName).trim());
                    } else {
                        params.put(paramName.trim(), request.getParameter(paramName).trim());
                    }
                }
                mtgRequest.setParams(params);
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String data = br.readLine();
                if (data != null) {
                    if (data.split("&").length > 1) {
                        for (String parameter : data.split("&")) {
                            String[] keyValue = parameter.split("=");
                            if (keyValue[0].equalsIgnoreCase("id")) {
                                mtgRequest.setId(keyValue[1]);
                            } else if (keyValue[0].equalsIgnoreCase("pid")) {
                                mtgRequest.setPid(keyValue[1]);
                            } else if (keyValue[0].equalsIgnoreCase("uid")) {
                                mtgRequest.setUid(keyValue[1]);
                            } else {
                                params.put(keyValue[0], keyValue[1]);
                            }
                        }
                    } else {
                        String[] keyValue = data.split("=");
                        if (keyValue[0].equalsIgnoreCase("id")) {
                            mtgRequest.setId(keyValue[1]);
                        } else if (keyValue[0].equalsIgnoreCase("pid")) {
                            mtgRequest.setPid(keyValue[1]);
                        } else if (keyValue[0].equalsIgnoreCase("uid")) {
                            mtgRequest.setUid(keyValue[1]);
                        } else {
                            params.put(keyValue[0], keyValue[1]);
                        }
                    }
                }
                mtgRequest.setParams(params);
            }
        } else if (contentType.contains("application/json")) {
            String line = "";
            StringBuilder data = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                while ((line = br.readLine()) != null) {
                    data.append(line);
                }
            }
            JSONObject jsonParams = new JSONObject(data.toString());
            Iterator jsonIterator = jsonParams.keys(); //gets all the keys
            while (jsonIterator.hasNext()) {
                String key = (String) jsonIterator.next(); // get key
                String value = (String) jsonParams.get(key); // get value
                if (key.equalsIgnoreCase("id")) {
                    mtgRequest.setId(value);
                } else if (key.equalsIgnoreCase("pid")) {
                    mtgRequest.setPid(value);
                } else if (key.equalsIgnoreCase("uid")) {
                    mtgRequest.setUid(value);
                } else {
                    params.put(key, value);
                }
            }
            mtgRequest.setParams(params);
        } else if (contentType.contains("multipart/form-data")) {
            Enumeration<String> parameters = request.getParameterNames();
            while (parameters.hasMoreElements()) {
                String paramName = parameters.nextElement();
                if (paramName.trim().equalsIgnoreCase("id")) {
                    mtgRequest.setId(request.getParameter(paramName).trim());
                } else if (paramName.trim().equalsIgnoreCase("pid")) {
                    mtgRequest.setPid(request.getParameter(paramName).trim());
                } else if (paramName.trim().equalsIgnoreCase("uid")) {
                    mtgRequest.setUid(request.getParameter(paramName).trim());
                } else {
                    params.put(paramName.trim(), request.getParameter(paramName).trim());
                }
            }
            mtgRequest.setParams(params);
        }
        return mtgRequest;
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                log("RestRouterFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("RestRouterFilter()");
        }
        StringBuilder sb = new StringBuilder("RestRouterFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}

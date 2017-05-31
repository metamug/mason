/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kainix
 */
public class ConsoleAuthFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;
    private String encoding;

    public ConsoleAuthFilter() {
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
        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }
//        String header = req.getHeader("Accept");
//        if (header != null && header.contains("xml")) {
//            response.setContentType("application/xml;charset=UTF-8");
//        } else {
//            response.setContentType("application/json;charset=UTF-8");
//        }
        res.setHeader("Access-Control-Allow-Origin", "*");
//        String token = req.getHeader("Autherization");
//        PrintWriter writer = response.getWriter();
//        response.setContentType("application/json;charset=UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        JSONObject obj = new JSONObject();
//        log("filter logging" + req.getServletPath());
//        if (req.getServletPath().contains("accessToken") || req.getServletPath().contains("user") || req.getServletPath().contains("index")||req.getServletPath().contains("docs")) {
        chain.doFilter(req, (ServletResponse) request);
//        } else if (token != null) {
//            AuthService authService = new AuthService();
//            int userId = authService.authorizeToken(token);
//            if (userId != 0) {
//                request.setAttribute("userId", userId);
//                chain.doFilter(request, response);
//            } else {
//                res.setStatus(401);
//                obj.put("message", "Unauthorized Request");
//                obj.put("status", 401);
//                writer.print(obj);
//                writer.flush();
//                writer.close();
//            }
//        } else {
//            res.setStatus(400);
//            obj.put("message", "Bad Request");
//            obj.put("status", 400);
//            writer.print(obj);
//            writer.flush();
//            writer.close();
//        }
    }

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
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    @Override
    public void destroy() {
    }
}

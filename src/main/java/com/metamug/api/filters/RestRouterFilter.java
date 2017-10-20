/**
 * ***********************************************************************
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
package com.metamug.api.filters;

import com.eclipsesource.json.ParseException;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.metamug.api.common.MtgRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        if (tokens.length > 2) {
            if (req.getServletPath().contains("index") || req.getServletPath().contains("docs")) {
                chain.doFilter(request, response);
            } else {
                response.setContentType("application/json");
                String contentType = req.getContentType() == null ? "" : req.getContentType();
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
                            req.setAttribute("mtgMethod", req.getMethod());
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
                    } catch (IOException | ServletException | JSONException | ParseException ex) {
                        try (ServletOutputStream writer = response.getOutputStream()) {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            JSONObject obj = new JSONObject();
                            res.setStatus(422);
                            obj.put("status", 422);
                            if (ex.getClass().toString().contains("com.eclipsesource.json.ParseException")) {

                                obj.put("message", "Could not parse the body of the request according to the provided Content-Type.");
                            } else if (ex.getCause() != null) {
                                String cause = ex.getCause().toString();
                                obj.put("message", cause.split(": ")[1].replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                            } else if (ex.getMessage().contains("ELException")) {
                                obj.put("message", "Incorrect test condition in '" + tokens[2] + "' resource");
                                obj.put("status", 512);
                                res.setStatus(512);
                            } else {
                                obj.put("message", ex.getMessage().replaceAll("(\\s|\\n|\\r|\\n\\r)+", " "));
                                Logger.getLogger(RestRouterFilter.class.getName()).log(Level.SEVERE, "Router " + tokens[2] + ":{0}", ex.getMessage());
                            }
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

    private MtgRequest createMtgResource(String[] tokens, String method, HttpServletRequest request) throws IOException, JSONException, ServletException, ParseException {
        MtgRequest mtgRequest = new MtgRequest();
        //Set parent value and pid
        if (tokens.length == 5 || tokens.length == 6) {
            mtgRequest.setParent(tokens[2]);
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
                        if (request.getParameterValues(paramName).length > 1) {
                            params.put(paramName.trim(), String.join(",", request.getParameterValues(paramName)).trim());
                        } else {
                            params.put(paramName.trim(), request.getParameter(paramName).trim().isEmpty() ? null : request.getParameter(paramName).trim());
                        }
                    }
                }
                mtgRequest.setParams(params);
            } else {
                String line = "";
                StringBuilder data = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                    while ((line = br.readLine()) != null) {
                        data.append(line);
                    }
                }
                if (contentType.contains("application/json")) {
                    Map<String, Object> flattenAsMap = JsonFlattener.flattenAsMap(data.toString());
                    for (Map.Entry<String, Object> entry : flattenAsMap.entrySet()) {
                        String key = entry.getKey();
                        String value = String.valueOf(entry.getValue());
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
                } else if (data.toString().split("&").length > 1) {
                    for (String parameter : data.toString().split("&")) {
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
                    String[] keyValue = data.toString().split("=");
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
                mtgRequest.setParams(params);
            }
        } else if (contentType.contains("application/json")) {
            String line = "";
            StringBuilder jsonData = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
            }
            Map<String, Object> flattenAsMap = JsonFlattener.flattenAsMap(jsonData.toString());
            for (Map.Entry<String, Object> entry : flattenAsMap.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
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

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
package com.metamug.api.listener;

import com.metamug.event.UploadEvent;
import com.metamug.event.UploadListener;
import com.mtg.io.objectreturn.ObjectReturn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
@WebServlet(name = "UploadController", urlPatterns = {"/index"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class UploadController extends HttpServlet {

    @Resource(name = "jdbc/mtgMySQL")
    private DataSource ds;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appName = request.getContextPath().split("/")[1];
        String contentType = request.getContentType() == null ? "" : request.getContentType();
        String acceptHeadr = request.getHeader("Accept") == null ? "" : request.getHeader("Accept");
        String acceptHeader = Arrays.asList(acceptHeadr.split("/")).contains("xml") ? "application/xml" : "application/json";
        JSONObject obj = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        if (contentType.contains("multipart/form-data")) {
            String uploadFilePath = System.getProperty("catalina.base") + File.separator + "uploads" + request.getContextPath();
            Files.createDirectories(Paths.get(uploadFilePath));
            try {
                String fileName = null;
                //Get all the parts from request and write it to the file on server
                List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">

                for (Part filePart : fileParts) {
                    fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                    File uploadedFile = new File(uploadFilePath + File.separator + fileName);
                    if (!uploadedFile.isDirectory()) {
                        try (FileOutputStream fos = new FileOutputStream(uploadedFile); InputStream fileContent = filePart.getInputStream()) {
                            int read;
                            byte[] bytes = new byte[1024];
                            while ((read = fileContent.read(bytes)) != -1) {
                                fos.write(bytes, 0, read);
                            }
                        }
                    }
                }
                Object result;
                if (fileName != null && !fileName.isEmpty()) {
                    result = callUploadEvent(new File(uploadFilePath + File.separator + fileName), appName, request);
                    response.setStatus(201);
                } else {
                    result = callUploadEvent(null, appName, request);
                    response.setStatus(200);
                }
                if (result != null) {
                    Object processedResult = ObjectReturn.convert(result, acceptHeader);
                    if (acceptHeader.equals("application/json")) {
                        try {
                            JSONObject resultJson = new JSONObject((String) processedResult);
                            JSONObject singleData = new JSONObject();
                            for (Iterator<String> iterator = resultJson.keys(); iterator.hasNext();) {
                                String key = iterator.next();
                                singleData.put(key, resultJson.get(key));
                            }
                            obj.append("response", singleData);
                        } catch (JSONException ex) {
                            obj.append("response", processedResult);
                        }
                    } else {
                        response.setStatus(204);
                    }
                } else {
                    response.setStatus(204);
                }
            } catch (IllegalStateException ex) {
                if (ex.getMessage().contains("FileSizeLimitExceededException")) {
                    obj.put("message", "File size exceed limit.");
                    obj.put("status", 413);
                    response.setStatus(413);
                }
            } catch (FileNotFoundException ex) {
                obj.put("message", "No implementation of UploadListener was found.");
                obj.put("status", 512);
                response.setStatus(512);
            } catch (ClassNotFoundException ex) {
                String timestamp = String.valueOf(System.currentTimeMillis());
                long hash = UUID.nameUUIDFromBytes(timestamp.getBytes()).getMostSignificantBits();
                String errorId = String.valueOf(Math.abs(hash));
                obj.put("message", "API Error. Please contact your API administrator.");
                obj.put("errorId", errorId);
                obj.put("status", 512);
                response.setStatus(512);
                obj.put("message", "No implementation of UploadListener was found.");
                logError(errorId, null, request, ex);
            } catch (NullPointerException | IOException | ServletException | InstantiationException | IllegalAccessException ex) {
                String timestamp = String.valueOf(System.currentTimeMillis());
                long hash = UUID.nameUUIDFromBytes(timestamp.getBytes()).getMostSignificantBits();
                String errorId = String.valueOf(Math.abs(hash));
                obj.put("message", "API Error. Please contact your API administrator.");
                obj.put("errorId", errorId);
                obj.put("status", 512);
                response.setStatus(512);
                StringBuilder errorTraceBuilder = new StringBuilder();
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement.getClassName().contains("UploadController")) {
                        break;
                    }
                    errorTraceBuilder.append(stackTraceElement).append("\n");
                }
                logError(errorId, errorTraceBuilder.toString(), request, ex);
            } catch (Exception ex) {
                String timestamp = String.valueOf(System.currentTimeMillis());
                long hash = UUID.nameUUIDFromBytes(timestamp.getBytes()).getMostSignificantBits();
                String errorId = String.valueOf(Math.abs(hash));
                obj.put("message", "API Error. Please contact your API administrator.");
                obj.put("errorId", errorId);
                obj.put("status", 512);
                response.setStatus(512);
                StringBuilder errorTraceBuilder = new StringBuilder();
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement.getClassName().contains("UploadController")) {
                        break;
                    }
                    errorTraceBuilder.append(stackTraceElement).append("\n");
                }
                logError(errorId, errorTraceBuilder.toString(), request, ex);
            }
        } else {
            obj.put("message", "Unsupported Media Type");
            obj.put("status", 415);
            response.setStatus(415);
        }
        if (obj.length() > 0) {
            response.setContentType("application/json");
            try (ServletOutputStream out = response.getOutputStream()) {
                out.print(obj.toString());
                out.flush();
            } catch (IOException ex) {
//                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(405);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(405);
    }

    private Object callUploadEvent(File uploadedFile, String appName, HttpServletRequest req) throws NullPointerException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, RuntimeException, Exception {
        String listenerClass;
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(new File(System.getProperty("catalina.base") + File.separator + "api" + "/" + appName + "/WEB-INF/config.properties"))) {
            prop.load(fis);
            listenerClass = prop.getProperty("UploadListener");
        }
        if (listenerClass != null) {
            Class cls = Class.forName((String) listenerClass);
            Object newInstance = cls.newInstance();
            UploadListener listener;
            if (UploadListener.class.isAssignableFrom(cls)) {
                listener = (UploadListener) newInstance;
                //Add Request Header values
                Map<String, String> reqHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Enumeration<String> headerNames = req.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String header = headerNames.nextElement();
                    if (req.getHeader(header) != null) {
                        reqHeaders.put(header, req.getHeader(header));
                    }
                }
                //Add Request Parameter values
                Map<String, String> reqParams = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Enumeration<String> parameterNames = req.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String param = parameterNames.nextElement();
                    if (req.getParameter(param) != null) {
                        reqParams.put(param, req.getParameter(param));
                    }
                }
                if (uploadedFile != null) {
                    return listener.uploadPerformed(new UploadEvent(uploadedFile, uploadedFile.getName(), reqParams, reqHeaders), ds);
                } else {
                    return listener.uploadPerformed(new UploadEvent(null, null, reqParams, reqHeaders), ds);
                }
            }
        } else {
            throw new ClassNotFoundException("No implementation of UploadListener was found.");
        }
        return null;
    }

    private void logError(String errorId, String trace, HttpServletRequest request, Exception exception) {
        String method = (String) request.getAttribute("mtgMethod");
        String resourceURI = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String exceptionMessage;
        if (exception.getMessage() != null) {
            exceptionMessage = exception.getMessage().replaceAll("(\\w+)_db\\.", "").replaceAll("(\\s|\\n|\\r|\\n\\r)+", " ");
        } else {
            exceptionMessage = exception.toString();
        }
        try (Connection con = ds.getConnection()) {
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO error_log (error_id,method,message,trace,resource) VALUES(?,?,?,?)");
            stmnt.setString(1, String.valueOf(errorId));
            stmnt.setString(2, method);
            stmnt.setString(3, exceptionMessage);
            stmnt.setString(4, trace);
            stmnt.setString(5, resourceURI);
            stmnt.execute();
        } catch (SQLException ex) {
//            Logger.getLogger(ExceptionTagHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, exception.getMessage(), exception);
    }
}

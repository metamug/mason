/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.listener;


import com.metamug.event.UploadEvent;
import com.metamug.event.UploadListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
@WebServlet(name = "UploadController", urlPatterns = {"/index"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appName = request.getContextPath().split("/")[1];
        String contentType = request.getHeader("Content-Type");
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
                    try (FileOutputStream fos = new FileOutputStream(uploadedFile); InputStream fileContent = filePart.getInputStream()) {
                        int read = 0;
                        byte[] bytes = new byte[1024];
                        while ((read = fileContent.read(bytes)) != -1) {
                            fos.write(bytes, 0, read);
                        }
                    }
                }
                callUploadEvent(new File(uploadFilePath + File.separator + fileName), appName, request);
                response.setStatus(201);
            } catch (IllegalStateException ex) {
                if (ex.getMessage().contains("FileSizeLimitExceededException")) {
                    response.setStatus(413);
                }
            } catch (ClassNotFoundException ex) {
                obj.put("message", "No implementation of UploadListener was found.");
                obj.put("status", 428);
                response.setStatus(428);
            } catch (IOException | ServletException | InstantiationException | IllegalAccessException ex) {
                obj.put("message", "Error occured in UploadListener implementation");
                obj.put("status", 500);
                response.setStatus(500);
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            response.setStatus(415);
        }
        if (obj.length() > 0) {
            response.setContentType("application/json");
            try (ServletOutputStream out = response.getOutputStream()) {
                out.print(obj.toString());
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void callUploadEvent(File uploadedFile, String appName, HttpServletRequest req) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File projectLibs = new File(System.getProperty("catalina.base") + File.separator + "api" + "/" + appName + "/WEB-INF/lib/");
        File[] jars = projectLibs.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("-MTG");
            }
        });
        String listenerClass = null;
        for (File jar : jars) {
            JarFile jarFile = new JarFile(jar);
            Enumeration allEntries = jarFile.entries();
            while (allEntries.hasMoreElements()) {
                JarEntry je = (JarEntry) allEntries.nextElement();
                if (!je.isDirectory() && je.getName().endsWith(".class")) {
                    String className = je.getName().substring(0, je.getName().length() - ".class".length());
                    className = className.replace('/', '.');
                    Class clazz = Class.forName(className);
                    Class[] interfaces = clazz.getInterfaces();
                    for (Class aInterface : interfaces) {
                        if (aInterface.getName().contains("UploadListener")) {
                            listenerClass = className;
                        }
                    }
                }
            }
        }
        if (listenerClass != null) {
            Class cls = Class.forName((String) listenerClass);
            Object newInstance = cls.newInstance();
            UploadListener listener;
            if (UploadListener.class.isAssignableFrom(cls)) {
                listener = (UploadListener) newInstance;
                Enumeration<String> headerNames = req.getHeaderNames();
                Map<String, String> reqHeaders = new HashMap<>();
                Map<String, String> reqParams = new HashMap<>();
                while (headerNames.hasMoreElements()) {
                    String header = headerNames.nextElement();
                    if (req.getHeader(header) != null) {
                        reqHeaders.put(header, req.getHeader(header));
                    }
                }
                Enumeration<String> parameterNames = req.getParameterNames();
                while (headerNames.hasMoreElements()) {
                    String param = parameterNames.nextElement();
                    if (req.getParameter(param) != null) {
                        reqParams.put(param, req.getParameter(param));
                    }
                }
                listener.uploadPerformed(new UploadEvent(uploadedFile, uploadedFile.getName(), reqParams, reqHeaders), ds);
            }
        } else {
            throw new ClassNotFoundException();
        }
    }
    
}

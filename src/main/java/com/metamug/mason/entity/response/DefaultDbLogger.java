/*
 * Copyright 2020 anishhirlekar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.metamug.mason.entity.response;

import com.metamug.mason.tag.ExceptionTagHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 *
 * @author anishhirlekar
 */
public class DefaultDbLogger implements DbLoggable {

    @Override
    public void log(String errorId, HttpServletRequest request, DataSource ds, String exceptionMessage, StringBuilder errorTraceBuilder) {
        String method = (String) request.getAttribute("mtgMethod");
        String resourceURI = (String) request.getAttribute("javax.servlet.forward.request_uri");
        try (Connection con = ds.getConnection(); PreparedStatement stmnt = con.prepareStatement("INSERT INTO error_log (error_id,request_method,message,trace,"
                + " resource) VALUES(?,?,?,?,?)");) {
            stmnt.setString(1, errorId);
            stmnt.setString(2, method);
            stmnt.setString(3, exceptionMessage);
            stmnt.setString(4, errorTraceBuilder.toString());
            stmnt.setString(5, resourceURI);
            stmnt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExceptionTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
}
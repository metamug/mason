/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import com.metamug.api.common.MtgRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
public class GroupTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String value;
    @Resource(name = "jdbc/mtgMySQL")
    private DataSource ds;

    /**
     * Creates new instance of tag handler
     */
    public GroupTagHandler() {
        super();
    }

    /**
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE. This method is automatically generated. Do not modify this method. Instead, modify the
     * methods that this method calls.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        MtgRequest mtg = (MtgRequest) pageContext.getRequest().getAttribute("mtgReq");
        String header = request.getHeader("Authorization");
        try {
            if (header != null) {
                if (header.contains("Basic ")) {
                    String token = header.replaceFirst("Basic ", "");
                    String userCred = new String(Base64.getDecoder().decode(token.getBytes()));
                    String[] split = userCred.split(":");
                    if (split.length > 1 && !split[0].isEmpty() && !split[1].isEmpty()) {
                        JSONObject status = validateUser(split[0], split[1]);
                        switch (status.getInt("status")) {
                            case -1:
                                throw new JspException("Forbidden Access to resource.", new RoleAccessDeniedException(""));
                            case 0:
                                throw new JspException("Access Denied to resource due to unauthorization.", new RoleAuthorizationException(""));
                            case 1:
                                mtg.setUid(String.valueOf(status.getInt("user_id")));
                                pageContext.getRequest().setAttribute("mtgReq", mtg);
                                break;
                        }
                    } else {
                        throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
                    }
                } else {
                    throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
                }
            } else {
                throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
            }
        } catch (IllegalArgumentException ex) {
            throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
        }
        return EVAL_PAGE;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        value = null;
    }

    private JSONObject validateUser(String userName, String password) {
        JSONObject status = new JSONObject();
        status.put("status", 0);
        try (Connection con = ds.getConnection();) {
            PreparedStatement statement = con.prepareStatement("SELECT user_id FROM user WHERE user_name=? AND password=?");
            statement.setString(1, userName);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PreparedStatement stmnt = con.prepareStatement("SELECT count(id) as total_count FROM user_role WHERE role_name=? AND user_id=?");
                    stmnt.setString(1, value);
                    stmnt.setInt(2, resultSet.getInt("user_id"));
                    try (ResultSet result = stmnt.executeQuery()) {
                        while (result.next()) {
                            if (result.getInt("total_count") > 0) {
                                status.put("status", 1);
                                status.put("user_id", resultSet.getInt("user_id"));
                            } else {
                                status.put("status", -1);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return status;
    }

    private static class RoleAuthorizationException extends Exception {

        public RoleAuthorizationException(String message) {
            super(message);
        }
    }

    private static class RoleAccessDeniedException extends Exception {

        public RoleAccessDeniedException(String message) {
            super(message);
        }
    }

}

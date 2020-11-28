package com.metamug.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    static int getInt(PreparedStatement statement, String colName) throws SQLException {
        ResultSet rs = statement.executeQuery();
        int value = -1;
        while (rs.next()) {
            value = rs.getInt(colName);
        }
        return value;
    }

    static String getString(PreparedStatement statement, String colName) throws SQLException {
        ResultSet rs = statement.executeQuery();
        String value = "";
        while (rs.next()) {
            value = rs.getString(colName);
        }
        return value;
    }

}

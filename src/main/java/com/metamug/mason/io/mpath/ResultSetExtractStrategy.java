package com.metamug.mason.io.mpath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author anishhirlekar
 */
public class ResultSetExtractStrategy implements ExtractStrategy{

    @Override
    public String extract(String path, Object target) {
        ResultSet rs = (ResultSet)target;
        
        int row = Integer.parseInt(MPathUtil.getRow(path));
        String colName = MPathUtil.getColumn(path);
        
        String value = null;
        
        try {
            while (rs.next()) {
                if(rs.getRow() == row){
                    value = rs.getString(colName);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultSetExtractStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
}

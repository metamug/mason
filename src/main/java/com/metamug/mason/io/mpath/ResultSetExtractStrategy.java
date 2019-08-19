package com.metamug.mason.io.mpath;

import java.sql.ResultSet;

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
        ResultSet result = (ResultSet)target;
        
        
        return null;
    }
    
}

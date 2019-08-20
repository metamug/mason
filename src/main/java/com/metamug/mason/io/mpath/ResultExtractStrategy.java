package com.metamug.mason.io.mpath;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author anishhirlekar
 */
public class ResultExtractStrategy extends ExtractStrategy<ResultImpl>{

    @Override
    public String extract(String path, ResultImpl target) {
        int rowIndex = 0;
        String colName = null;
        
        Pattern p = Pattern.compile("^\\$\\[(\\w+?)\\]\\[(\\d+?)\\]\\.(\\S+?)$");
        Matcher m = p.matcher(path);
        
        if(m.find()) {
            rowIndex = Integer.parseInt(m.group(2));
            colName = m.group(3);
        }else{
            throw new IllegalArgumentException(path+" not valid for result set");
        }
        
        //throw exception if given row index is greater than row count
        if( (rowIndex+1) > target.getRowCount() ) {
            throw new ArrayIndexOutOfBoundsException("Given row index [" + rowIndex + "] is greater"
                    + " than number of records ("+target.getRowCount()+") in SQL result.");
        }
        //throw exception if given column name not found in result
        if(!Arrays.asList(target.getColumnNames()).contains(colName)){
            throw new NoSuchElementException("Column name \""+colName+"\" not found in SQL result.");
        }
        
        SortedMap row = target.getRows()[rowIndex];
        
        return row.get(colName).toString();
    }
    
}

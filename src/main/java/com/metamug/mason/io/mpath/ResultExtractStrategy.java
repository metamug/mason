package com.metamug.mason.io.mpath;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.SortedMap;
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
public class ResultExtractStrategy implements ExtractStrategy{

    @Override
    public String extract(String path, Object target) {
        ResultImpl ri = (ResultImpl)target;
  
        int rowIndex = Integer.parseInt(MPathUtil.getRow(path));
        //throw exception if given row index is greater than row count
        if( (rowIndex+1) > ri.getRowCount() ) {
            throw new ArrayIndexOutOfBoundsException("Given row index [" + rowIndex + "] is greater"
                    + " than number of records ("+ri.getRowCount()+") in SQL result.");
        }
        //throw exception if given column name not found in result
        String colName = MPathUtil.getColumn(path);
        if(!Arrays.asList(ri.getColumnNames()).contains(colName)){
            throw new NoSuchElementException("Column name \""+colName+"\" not found in SQL result.");
        }
        
        SortedMap row = ri.getRows()[rowIndex];
        return row.get(colName).toString();
    }
    
}

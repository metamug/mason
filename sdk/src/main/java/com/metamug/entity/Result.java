/*
 * Copyright 2019 pc.
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
package com.metamug.entity;

import java.util.Map;

/**
 * SQL Query Result. 
 *
 * @author themetamug
 */
public class Result {
    
    //@TODO Can this be made final or immutable 
    
    private Map[] recordMap;
    private String[] columnNames;
    private int rowCount;

    public Result(Map[] rowMap, String[] columnNames, int rowCount) {
        this.recordMap = rowMap;
        this.columnNames = columnNames;
        this.rowCount = rowCount;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public int getRowCount() {
        return rowCount;
    }

    public Map[] getRecordMap() {
        return recordMap;
    }

}

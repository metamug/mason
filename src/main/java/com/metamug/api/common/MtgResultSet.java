/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.common;

import java.util.List;
import org.apache.taglibs.standard.tag.common.sql.ResultImpl;

/**
 *
 * @author Kaisteel
 */
public class MtgResultSet {

    private List<ResultImpl> result;
    private List<String> id;

    public MtgResultSet() {
    }

    public void setResult(ResultImpl result) {
        this.result.add((ResultImpl) result);
    }

    public ResultImpl getResult() throws InstantiationException, IllegalAccessException {
        return ResultImpl.class.newInstance();
    }

    public List<ResultImpl> getResultList() {
        return result;
    }

    public String getId() {
        return "";
    }

    public List<String> getIdList() {
        return id;
    }

    public void setId(String id) {
        this.id.add(id);
    }
}

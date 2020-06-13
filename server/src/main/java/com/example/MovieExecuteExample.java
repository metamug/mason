/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.entity.Customer;
import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.entity.Result;
import com.metamug.exec.RequestProcessable;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author pc
 */
public class MovieExecuteExample implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {

        Result result = (Result) args.get("movieInfo");

        Response response = new Response();

        // set your model object as payload here
        response.setPayload(result.getRowCount());
        return response;
    }

}

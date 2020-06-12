package com.example;

import com.example.entity.Customer;
import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;

import javax.sql.DataSource;
import java.util.Map;


/**
 *
 * @author anishhirlekar
 */
public class RequestHandler implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {


        Customer customer = new Customer();
        customer.setName("John Doeyy.");
        customer.setId(8);
        customer.setRoll(555);
        customer.setContact("+1 943 322 4292", "john.doe@gmail.com");

        Response response = new Response();


        // set your model object as payload here
        response.setPayload(customer);
        return response;
    }

}
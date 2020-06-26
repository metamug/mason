package com.metamug.mason.entity.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.mason.entity.response.ResponseMarshaller;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class ObjectReturnTest {

    private Customer customer1, customer2, customer3;
    private final List<Customer> list = new ArrayList<>();

    @Before
    public void init() {
        customer1 = new Customer(1, "John", "Doe");
        PhoneNumber pn = new PhoneNumber();
        pn.setNum("9128992849");
        pn.setType("mobile");
        customer1.addPhoneNumber(pn);
        list.add(customer1);

        customer2 = new Customer(2, "Al", "Fred");
        pn = new PhoneNumber();
        pn.setNum("1204597612");
        pn.setType("work");
        customer2.addPhoneNumber(pn);
        list.add(customer2);

        customer3 = new Customer(3, "Mason", "Rich");
        pn = new PhoneNumber();
        pn.setNum("1642953181");
        pn.setType("work");
        customer3.addPhoneNumber(pn);
        list.add(customer3);
    }

    @Test
    public void ObjectToJsonTest() {
        try {
            String resultJson = ResponseMarshaller.convert(customer1, ResponseMarshaller.TYPE_JSON);
            //System.out.println(resultJson);
            JSONObject jsonObject = new JSONObject(resultJson);
            Assert.assertNotNull(jsonObject);
        } catch (JSONException | JAXBException | JsonProcessingException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void ObjectToXmlTest() {
        try {
            String resultXml = ResponseMarshaller.convert(customer1, ResponseMarshaller.TYPE_XML);
            //    System.out.println(resultXml);
            Assert.assertNotNull(resultXml);
        } catch (JAXBException | JsonProcessingException ex) {
            Assert.fail(ex.toString());
        }
    }

    @Test
    public void StringTest() {
        try {
            String result = ResponseMarshaller.convert("Response String", "Ignored header");
            //  System.out.println(result);
            Assert.assertNotNull(result);
        } catch (JAXBException | JsonProcessingException ex) {
            Assert.fail(ex.toString());
        }
    }

    //object lists conversion not yet supported
    @Ignore
    @Test
    public void ObjectListToJsonTest() {
        try {
            String result = ResponseMarshaller.convert(list, ResponseMarshaller.TYPE_JSON);
            //System.out.println(result);
            JSONArray jsonArray = new JSONArray(result);
            Assert.assertNotNull(jsonArray);
        } catch (JSONException | JAXBException | JsonProcessingException e) {
            Assert.fail(e.toString());
        }
    }

    @Ignore
    @Test
    public void ObjectListToXml() {
        try {
            String result = ResponseMarshaller.convert(list, ResponseMarshaller.TYPE_XML);
            //System.out.println(result);
            Assert.assertNotNull(result);
        } catch (JAXBException | JsonProcessingException ex) {
            Assert.fail(ex.toString());
        }
    }

}

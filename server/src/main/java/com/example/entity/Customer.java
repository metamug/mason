package com.example.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author deepak
 */
@XmlRootElement
public class Customer {
    private int id;
    private int roll;
    private String name;
    @XmlElement(name = "contact")
    private Contact contact;

    public void setName(String n) {
        name = n;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public void setContact(String phone, String email) {
        contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
    }

    public int getId() {
        return id;
    }

    public int getRoll() {
        return roll;
    }

    public String getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }
}
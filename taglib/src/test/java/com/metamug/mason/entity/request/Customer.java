package com.metamug.mason.entity.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer {
    private int id;
    private int roll;
    private String name;

    public void setName(String n) {
        name = n;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoll(int roll) {
        this.roll = roll;
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

}

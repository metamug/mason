package com.example.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anishhirlekar
 */
public class Contact {
    private String phone;
    private String email;

    public void setPhone(String ph) {
        phone = ph;
    }

    public void setEmail(String e) {
        email = e;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
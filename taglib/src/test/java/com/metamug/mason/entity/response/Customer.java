package com.metamug.mason.entity.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer")
public class Customer {

    @XmlAttribute
    private int id;

    private String firstName;

    private String lastName;

    private final List<PhoneNumber> phoneNumbers = new ArrayList<>();

    //must have zero argument constructor
    public Customer() {

    }

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addPhoneNumber(PhoneNumber pn) {
        phoneNumbers.add(pn);
    }

}

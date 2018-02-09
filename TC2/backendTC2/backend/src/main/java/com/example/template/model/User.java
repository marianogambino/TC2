package com.example.template.model;

import java.util.List;

/**
 * Created by mariano on 08/02/18.
 */
public class User {

    private String numTel;
    private String token;
    private List<Contact> contacts;

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

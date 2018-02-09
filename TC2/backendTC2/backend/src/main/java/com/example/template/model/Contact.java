package com.example.template.model;

/**
 * Created by mariano on 07/02/18.
 */
public class Contact {

    private final String numTel;
    private final String name;

    public Contact(String numTel, String name) {
        this.numTel = numTel;
        this.name = name;
    }

    public String getNumTel() {
        return numTel;
    }


    public String getName() {
        return name;
    }

}

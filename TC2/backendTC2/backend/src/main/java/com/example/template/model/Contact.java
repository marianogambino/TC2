package com.example.template.model;

/**
 * Created by mariano on 07/02/18.
 */
public class Contact {

    private final String phoneNumber;
    private final String name;
    private String permission;

    public Contact(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

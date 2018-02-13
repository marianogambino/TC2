package com.example.template.request;

import com.example.template.model.Contact;
import com.example.template.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 12/02/18.
 */
public class UserRequest {

    private User usuario;
    private List<Contact> contactos = new ArrayList<>();

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<Contact> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contact> contactos) {
        this.contactos = contactos;
    }
}

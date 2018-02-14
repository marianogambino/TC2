package unimoron.ar.edu.request;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;

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


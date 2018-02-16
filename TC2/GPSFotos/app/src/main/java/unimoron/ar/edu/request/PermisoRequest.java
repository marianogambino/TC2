package unimoron.ar.edu.request;

import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 15/02/18.
 */

public class PermisoRequest {

    private Contact contacto;
    private User usuario;
    private Boolean conPermiso = false;

    public Contact getContacto() {
        return contacto;
    }

    public void setContacto(Contact contacto) {
        this.contacto = contacto;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Boolean getConPermiso() {
        return conPermiso;
    }

    public void setConPermiso(Boolean conPermiso) {
        this.conPermiso = conPermiso;
    }
}

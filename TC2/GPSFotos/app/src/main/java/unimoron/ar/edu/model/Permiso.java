package unimoron.ar.edu.model;

/**
 * Created by mariano on 13/02/18.
 */

public class Permiso {

    private Long id;
    private Contact contacto = new Contact();
    private Boolean conPermiso = false;

    public Contact getContacto() {
        return contacto;
    }

    public void setContacto(Contact contacto) {
        this.contacto = contacto;
    }


    public Boolean getConPermiso() {
        return conPermiso;
    }

    public void setConPermiso(Boolean conPermiso) {
        this.conPermiso = conPermiso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

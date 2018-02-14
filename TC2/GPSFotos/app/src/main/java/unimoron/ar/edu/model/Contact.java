package unimoron.ar.edu.model;

/**
 * Created by mariano on 15/12/17.
 */

public class Contact {

    private String phoneNumber;
    private String name;
    private String token;
    private Boolean available = false;
    private Boolean isNoficable = false;
    private String permisoDescripcion = "Sin Permiso de Publicacion";


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPermisoDescripcion() {
        return permisoDescripcion;
    }

    public void setPermisoDescripcion(String permisoDescripcion) {
        this.permisoDescripcion = permisoDescripcion;
    }

    public Boolean getNoficable() {
        return isNoficable;
    }

    public void setNoficable(Boolean noficable) {
        isNoficable = noficable;
    }
}

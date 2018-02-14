package unimoron.ar.edu.model;

import java.util.Date;

/**
 * Created by mariano on 13/02/18.
 */

public class Notificacion {

    private String descripcion;
    private String fromName;
    private String fromNumTel;
    private Date fechaNotificacion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromNumTel() {
        return fromNumTel;
    }

    public void setFromNumTel(String fromNumTel) {
        this.fromNumTel = fromNumTel;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }
}

package unimoron.ar.edu.model;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by mariano on 20/02/18.
 */

public class Publicacion {

    private Date fechaPublicacion;
    private Photo photo;
    private String tituloPublicacion;
    private String urlFoto;
    private String photoJson;

    public Publicacion(){
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
        setPhotoJson();
    }

    public String getTituloPublicacion() {
        return tituloPublicacion;
    }

    public void setTituloPublicacion(String tituloPublicacion) {
        this.tituloPublicacion = tituloPublicacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public void setPhotoJson(String photoJson) {
        this.photoJson = photoJson;
    }

    private void setPhotoJson(){
        Gson gson = new Gson();
        this.photoJson = gson.toJson(this.photo);
    }

    public String getPhotoJson() {
        return photoJson;
    }
}

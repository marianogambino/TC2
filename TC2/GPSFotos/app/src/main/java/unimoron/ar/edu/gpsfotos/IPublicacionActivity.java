package unimoron.ar.edu.gpsfotos;

import java.util.List;

import unimoron.ar.edu.model.Publicacion;

/**
 * Created by mariano on 21/02/18.
 */

public interface IPublicacionActivity {


    void showProgress(final boolean show);
    void setPublicaciones(List<Publicacion> publicaciones);

}

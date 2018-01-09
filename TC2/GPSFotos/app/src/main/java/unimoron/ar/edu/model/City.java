package unimoron.ar.edu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 17/12/17.
 */

public class City {

    private Long id;
    private String name;
    private List<Photo> photos = new ArrayList<Photo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

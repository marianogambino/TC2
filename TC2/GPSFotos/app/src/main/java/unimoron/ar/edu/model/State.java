package unimoron.ar.edu.model;

/**
 * Created by mariano on 17/12/17.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * State or Province
 */
public class State {

    private Long id;
    private String name;
    private List<City> city = new ArrayList<City>();

    public State(){
    }


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

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}

package unimoron.ar.edu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 17/12/17.
 */

public class Country {

    private Long id;
    private String name;
    private List<State> state = new ArrayList<State>();

    public Country(){
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

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }
}

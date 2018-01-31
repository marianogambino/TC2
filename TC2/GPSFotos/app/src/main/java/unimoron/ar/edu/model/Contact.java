package unimoron.ar.edu.model;

/**
 * Created by mariano on 15/12/17.
 */

public class Contact {

    private String phoneNumber;
    private String name;
    private Boolean available = false;


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
}

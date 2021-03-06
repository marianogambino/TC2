package unimoron.ar.edu.model;

/**
 * Created by mariano on 15/12/17.
 */

public class Location {

    private final double latitude;
    private final double longitude;

    private final String country;
    private final String state;
    private final String city;
    private final String address;

    public Location(double latitude, double longitude, String country, String state, String city, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}

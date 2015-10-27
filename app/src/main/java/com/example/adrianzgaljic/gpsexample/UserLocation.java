package com.example.adrianzgaljic.gpsexample;

/**
 * Created by adrianzgaljic on 21/10/15.
 */
public class UserLocation {


    private String username;
    private Double longitude;
    private Double latitude;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public UserLocation(String username, Double longitude, Double latitude){
        this.username = username;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}

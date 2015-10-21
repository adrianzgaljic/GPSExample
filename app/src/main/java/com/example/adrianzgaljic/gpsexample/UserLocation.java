package com.example.adrianzgaljic.gpsexample;

/**
 * Created by adrianzgaljic on 21/10/15.
 */
public class UserLocation {

    public String username;
    public Double longitude;
    public Double latitude;

    public UserLocation(String username, Double longitude, Double latitude){
        this.username = username;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}

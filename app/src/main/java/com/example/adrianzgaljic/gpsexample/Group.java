package com.example.adrianzgaljic.gpsexample;

import java.util.ArrayList;

/**
 * Created by adrianzgaljic on 07/11/15.
 */
public class Group {

    private  ArrayList<String> friends = new ArrayList<String>();


    public Group(String name){
        this.name = name;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


}

package com.example.adrianzgaljic.gpsexample;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by adrianzgaljic on 17/10/15.
 */
public class UserInfo {



    private static UserInfo userInfo = new UserInfo();
    private static String username;
    private static int color = Color.BLUE;
    private static Bitmap profilePicture;
    private static ArrayList<String> friendRequests = new ArrayList<String>();
    private static ArrayList<String> friends = new ArrayList<String>();

    public static UserInfo getUserInfo(){
        return userInfo;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInfo.username = username;
    }

    public static int getColor() {
        return color;
    }

    public static void setColor(int color) {
        UserInfo.color = color;
    }

    public static Bitmap getProfilePicture() {
        return profilePicture;
    }

    public static void setProfilePicture(Bitmap profilePicture) {
        UserInfo.profilePicture = profilePicture;
    }

    public static ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public static void setFriendRequests(ArrayList<String> friendRequests) {
        UserInfo.friendRequests = friendRequests;
    }

    public static ArrayList<String> getFriends() {
        return friends;
    }

    public static void setFriends(ArrayList<String> friends) {
        UserInfo.friends = friends;
    }


}

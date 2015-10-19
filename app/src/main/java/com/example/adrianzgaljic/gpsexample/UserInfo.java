package com.example.adrianzgaljic.gpsexample;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by adrianzgaljic on 17/10/15.
 */
public class UserInfo {



    private static UserInfo userInfo = new UserInfo();
    public static String username;
    public static int color = Color.BLUE;
    public static Bitmap profilePicture;


    public static UserInfo getUserInfo(){
        return userInfo;
    }
}

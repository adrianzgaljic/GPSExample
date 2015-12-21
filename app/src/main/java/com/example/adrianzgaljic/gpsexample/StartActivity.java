package com.example.adrianzgaljic.gpsexample;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;


public class StartActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent;
        String username = prefs.getString("username","");
        String address="http://192.168.5.84:80";

        if (username.equals(""))
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            String link = address+"/android_connect/get_color.php?user="+username;
            DBCheckUser checkUser = new DBCheckUser(link);
            checkUser.execute();
            while (checkUser.getResult()==null);

            UserInfo.setUsername(username);
            if (checkUser.getResult().equals("red")){
                UserInfo.setColor(Color.RED);
            } else if (checkUser.getResult().equals("green")){
                UserInfo.setColor(Color.GREEN);
            } else if (checkUser.getResult().equals("yellow")){
                UserInfo.setColor(Color.YELLOW);
            } else {
                UserInfo.setColor(Color.BLUE);
            }

            UserInfo.setProfilePicture(BitmapFactory.decodeResource(StartActivity.this.getResources(),
                    R.drawable.default_pp));
            UserInfo.setFriendRequests(LogInActivity.getFriendRequest(username));
            UserInfo.setFriends(LogInActivity.getFriends(username));
            //First Time App launched, you are putting isInitialAppLaunch to false and calling create password activity.
            intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }

    }


}


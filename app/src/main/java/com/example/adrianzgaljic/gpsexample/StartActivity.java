package com.example.adrianzgaljic.gpsexample;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;



public class StartActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent;
        String first = prefs.getString("created","");

        if (first.equals("created"))
        {
            intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        }
        else
        {
            //First Time App launched, you are putting isInitialAppLaunch to false and calling create password activity.
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

    }


}


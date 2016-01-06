package com.example.adrianzgaljic.gpsexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class Connect extends AppCompatActivity {

    private String strResponse = "";
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public String connect(final String urlStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {

                    url= new URL(urlStr);

                    URLConnection yc = url.openConnection();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    yc.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        strResponse = strResponse + inputLine;
                    }

                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                flag = false;
            }
        }).start();

        while(flag){}

        return strResponse;


    }





}

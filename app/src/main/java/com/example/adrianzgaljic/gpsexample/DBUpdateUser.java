package com.example.adrianzgaljic.gpsexample;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DBUpdateUser extends AsyncTask<String,Void,String> {
    private TextView statusField, roleField;
    private String username;
    private String color;



    public static String TAG = "logIspis";

    //flag 0 means get and 1 means post.(By default it is get.)
    public DBUpdateUser(String username, String color) {

        this.username = username;
        this.color = color;


    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {


        try {


            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();


            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(Configuration.address+"update_user.php?user=ado&color=red");
            pairs.add(new BasicNameValuePair("user",username ));
            pairs.add(new BasicNameValuePair("color",color ));

            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);




        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
        return "";
    }


    @Override
    protected void onPostExecute(String result){

    }
}
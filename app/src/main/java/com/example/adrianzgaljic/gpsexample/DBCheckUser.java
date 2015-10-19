package com.example.adrianzgaljic.gpsexample;



import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DBCheckUser extends AsyncTask<String,Void,String> {
    private TextView statusField,roleField;
    private String link;
    public String result = null;


    public static String TAG = "logIspis";

    //flag 0 means get and 1 means post.(By default it is get.)
    public DBCheckUser(String link) {
        this.link = link;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {


        try{



            HttpClient client;
            client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));

            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line="";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            result = sb.toString();
            Log.i(TAG, "...REZ..." + sb.toString());
            return sb.toString();
        }

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    public String getResult(){
        return result;
    }

    @Override
    protected void onPostExecute(String result){

    }
}
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

public class DBCreateUser extends AsyncTask<String,Void,String> {
    private TextView statusField,roleField;
    private String username;
    private String pass;

    public static String TAG = "logIspis";

    //flag 0 means get and 1 means post.(By default it is get.)
    public DBCreateUser(String username, String pass) {


        this.username = username;
        this.pass = pass;


    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {


        try{


            String link = "http://192.168.5.93:8080/android_connect/create_user.php?user="+username+"&pass="+pass;

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

            Log.i(TAG, "...REZ..." + sb.toString());
            return sb.toString();
        }

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String result){

    }
}
package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adrianzgaljic on 17/10/15.
 */
public class LogInActivity  extends Activity{

    private  ProgressBar spinner;
    public static final String TAG = "logIspis";

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        final EditText etUsername = (EditText) findViewById(R.id.etUsernameLogIn);
        final EditText etPass = (EditText) findViewById(R.id.etPasswordLogIn);

        Button btnLogin = (Button) findViewById(R.id.btnLogIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();

                if (username.equals("")){
                    Toast.makeText(LogInActivity.this,"Usename fied is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equals("")){
                    Toast.makeText(LogInActivity.this,"Password fied is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                spinner.setVisibility(View.VISIBLE);
                String link = "http://192.168.5.93:8080/android_connect/login.php?user="+username+"&pass="+pass;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult()==null);
                spinner.setVisibility(View.GONE);
                if (checkUser.getResult().equals("1")){
                    link = "http://192.168.5.93:8080/android_connect/get_color.php?user="+username;
                    checkUser = new DBCheckUser(link);
                    checkUser.execute();
                    while (checkUser.getResult()==null);
                    Toast.makeText(LogInActivity.this,"success",Toast.LENGTH_SHORT).show();
                    UserInfo.username = username;
                    if (checkUser.getResult().equals("red")){
                        UserInfo.color = Color.RED;
                    } else if (checkUser.getResult().equals("green")){
                        UserInfo.color = Color.GREEN;
                    } else if (checkUser.getResult().equals("yellow")){
                        UserInfo.color = Color.YELLOW;
                    } else {
                        UserInfo.color = Color.BLUE;
                    }

                        UserInfo.profilePicture = BitmapFactory.decodeResource(LogInActivity.this.getResources(),
                            R.drawable.default_pp);
                        UserInfo.friendRequests = getFriendRequest(username);
                        UserInfo.friends = getFriends(username);
                    SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username",username);
                    editor.apply();
                    Intent intent = new Intent(LogInActivity.this,MapsActivity.class);
                    startActivity(intent);
                } else if (checkUser.getResult().equals("0")){
                    Toast.makeText(LogInActivity.this,"Wrong pass or username",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(LogInActivity.this,"Can not connect sever",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static ArrayList<String> getFriendRequest(String username){
        ArrayList<String> requests = new ArrayList<String>();
        String link = "http://192.168.5.93:8080/android_connect/find_requests.php?user=" + username;
        DBCheckUser checkUser = new DBCheckUser(link);
        checkUser.execute();
        while (checkUser.getResult() == null) ;
        String result = checkUser.getResult();
        requests.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));
        Log.i(TAG, "result= " + result);
        return requests;

    }

    public static ArrayList<String> getFriends(String username){
        ArrayList<String> requests = new ArrayList<String>();
        String link = "http://192.168.5.93:8080/android_connect/get_friends.php?user=" + username;
        DBCheckUser checkUser = new DBCheckUser(link);
        checkUser.execute();
        while (checkUser.getResult() == null) ;
        String result = checkUser.getResult();
        requests.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));
        Log.i(TAG,"result= "+result);
        return requests;

    }

    public static ArrayList<UserLocation> getFriendsLocations(String username){

        ArrayList<String> friends = new ArrayList<String>();
        String link = "http://192.168.5.93:8080/android_connect/get_permissions.php?user=" + username;
        DBCheckUser checkUser = new DBCheckUser(link);
        checkUser.execute();
        while (checkUser.getResult() == null) ;

        friends.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));

        ArrayList<UserLocation> friendLocations = new ArrayList<UserLocation>();
        for (String user: friends) {
            link = "http://192.168.5.93:8080/android_connect/get_position.php?user=" + user;
            checkUser = new DBCheckUser(link);
            checkUser.execute();
            while (checkUser.getResult() == null) ;
            ArrayList<String> positions = new ArrayList<String>();
            positions.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));
            Double longitude = Double.parseDouble(positions.get(0));
            Double latitude = Double.parseDouble(positions.get(1));
            UserLocation userLocation = new UserLocation(user,longitude,latitude);
            friendLocations.add(userLocation);

        }
        return  friendLocations;


    }
}

package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by adrianzgaljic on 17/10/15.
 */
public class RegistrationActivity  extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPass = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPass = (EditText) findViewById(R.id.etConfirmPassword);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();

                if (username.equals("")){
                    Toast.makeText(RegistrationActivity.this,"Usename fied is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equals("")){
                    Toast.makeText(RegistrationActivity.this,"Password fied is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPass.equals("")){
                    Toast.makeText(RegistrationActivity.this,"Confirm password fied is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirmPass.equals(pass)){
                    Toast.makeText(RegistrationActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                String link = "http://192.168.5.93:8080/android_connect/check_user.php?user="+username;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult()==null);
                if (checkUser.getResult().equals("1")){
                    Toast.makeText(RegistrationActivity.this,"Username allready exists",Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkUser.getResult().equals("0")){
                    new DBCreateUser(username,pass).execute();
                    UserInfo.username = username;
                    UserInfo.color = Color.BLUE;
                    UserInfo.profilePicture = BitmapFactory.decodeResource(RegistrationActivity.this.getResources(),
                            R.drawable.default_pp);
                    Intent intent = new Intent(RegistrationActivity.this,MapsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationActivity.this,"Can not connect to server",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btnProba = (Button) findViewById(R.id.btnProba);
        btnProba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUpdateUser updateUser = new DBUpdateUser("ado","green");
                updateUser.execute();
            }
        });


    }
}

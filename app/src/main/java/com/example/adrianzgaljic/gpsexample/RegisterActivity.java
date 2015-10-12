package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by adrianzgaljic on 12/10/15.
 */
public class RegisterActivity extends Activity {

    private String name;
    private String surname;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                surname = etSurname.getText().toString();


                if (name.equals("")){
                    Toast.makeText(RegisterActivity.this,"Name field empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (surname.equals("")){
                    Toast.makeText(RegisterActivity.this,"Surname field empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name",name);
                editor.putString("surname",surname);
                editor.putString("created", "created");
                editor.apply();
                Intent intent = new Intent(RegisterActivity.this,IntroActivity.class);
                startActivity(intent);



            }
        });





    }
}

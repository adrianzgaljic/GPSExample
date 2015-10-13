package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class that handles user registration.
 *
 * Created by adrianzgaljic on 12/10/15.
 */
public class RegisterActivity extends Activity {

    // users name
    private String name;
    // users surname
    private String surname;
    // request code for photo picker intent
    private static final int SELECT_PHOTO = 100;
    // log TAG
    public static final String TAG = "logIspis";
    //iv that shows choosen PP
    private ImageView ivRegisterPP;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnChoosePP = (Button) findViewById(R.id.btnChoosePP);
        ivRegisterPP = (ImageView) findViewById(R.id.ivPP);

        btnChoosePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                String uristr = uri.toString();

                SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("PPImageURI",uristr);
                editor.apply();

                uri = Uri.parse(uristr);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.ivRegisterPP);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by adrianzgaljic on 13/10/15.
 */
public class IntroActivity extends Activity {

    // log TAG
    public static final String TAG = "logIspis";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        TextView etHi = (TextView) findViewById(R.id.tvHello);
        ImageView ivPP = (ImageView) findViewById(R.id.ivPP);

        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent;
        String name = prefs.getString("name","");
        etHi.setText("Hello "+name);
        String imageURIStr = prefs.getString("PPImageURI","");

        try {
            Uri imageUri = Uri.parse(imageURIStr);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ivPP.setImageBitmap(bitmap);
        } catch(Exception e){
            Log.e(TAG, "Could not find photo " + e);
        }
    }
}

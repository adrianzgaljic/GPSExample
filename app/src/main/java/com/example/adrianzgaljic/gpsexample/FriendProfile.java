package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

/**
 * Created by adrianzgaljic on 13/10/15.
 */
public class FriendProfile extends Activity {


    // log TAG
    public static final String TAG = "logIspis";
    ImageView ivPoint;
    ImageView ivPP;
    int color = 0;
    String user;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        color = intent.getIntExtra("color",Color.YELLOW);





        TextView etHi = (TextView) findViewById(R.id.tvUsernameFriend);
        etHi.setText(user);
        ivPP = (ImageView) findViewById(R.id.ivPPFriend);


        Button btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FriendProfile.this,"Friend request sent",Toast.LENGTH_SHORT).show();
            }
        });

        ivPoint = (ImageView) findViewById(R.id.ivPointFriend);
        switch (color) {
            case (Color.BLUE):
                ivPoint.setImageResource(R.drawable.circle_blue);
                break;
            case (Color.RED):
                ivPoint.setImageResource(R.drawable.circle_red);
                break;
            case (Color.GREEN):
                ivPoint.setImageResource(R.drawable.circle_green);
                break;
            case (Color.YELLOW):
                ivPoint.setImageResource(R.drawable.circle_yellow);
                break;
        }


    }




}

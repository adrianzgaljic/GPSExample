package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adrianzgaljic on 13/10/15.
 */
public class UserProfile extends Activity {

    // log TAG
    public static final String TAG = "logIspis";
    ImageView ivPoint;
    ImageView ivPP;
    String color = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        TextView etHi = (TextView) findViewById(R.id.tvUsername);
        ivPP = (ImageView) findViewById(R.id.ivPP);
        etHi.setText(UserInfo.username);

        ivPoint = (ImageView) findViewById(R.id.ivPoint);
        switch (UserInfo.color){
            case (Color.BLUE):
                ivPoint.setImageResource(R.drawable.circle_blue);
                color = "blue";
                break;
            case (Color.RED):
                ivPoint.setImageResource(R.drawable.circle_red);
                color = "red";
                break;
            case (Color.GREEN):
                ivPoint.setImageResource(R.drawable.circle_green);
                color = "green";
                break;
            case (Color.YELLOW):
                ivPoint.setImageResource(R.drawable.circle_yellow);
                color = "yellow";
                break;
        }

        ivPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserProfile.this);
                dialog.setTitle("Choose pin collor");
                dialog.setContentView(R.layout.dialog_pick_color);
                dialog.show();

                ImageView pointBlue = (ImageView)dialog.findViewById(R.id.ivPointBlueDialog);
                ImageView pointRed = (ImageView)dialog.findViewById(R.id.ivPointRedDialog);
                ImageView pointGreen = (ImageView)dialog.findViewById(R.id.ivPointGreenDialog);
                ImageView pointYellow = (ImageView)dialog.findViewById(R.id.ivPointYellowDialog);



                pointBlue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivPoint.setImageResource(R.drawable.circle_blue);
                        UserInfo.color = Color.BLUE;
                        color = "blue";
                        DBUpdateUser updateUser = new DBUpdateUser(UserInfo.username,color);
                        updateUser.execute();
                        dialog.dismiss();
                    }
                });

                pointRed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivPoint.setImageResource(R.drawable.circle_red);
                        UserInfo.color = Color.RED;
                        color = "red";
                        DBUpdateUser updateUser = new DBUpdateUser(UserInfo.username,color);
                        updateUser.execute();
                        dialog.dismiss();
                    }
                });

                pointYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivPoint.setImageResource(R.drawable.circle_yellow);
                        UserInfo.color = Color.YELLOW;
                        color = "yellow";
                        DBUpdateUser updateUser = new DBUpdateUser(UserInfo.username,color);
                        updateUser.execute();
                        dialog.dismiss();
                    }
                });

                pointGreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivPoint.setImageResource(R.drawable.circle_green);
                        UserInfo.color = Color.GREEN;
                        color = "green";
                        DBUpdateUser updateUser = new DBUpdateUser(UserInfo.username, color);
                        updateUser.execute();
                        dialog.dismiss();
                    }
                });



            }
        });

        ivPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(UserProfile.this);
            }
        });

/*
        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent;
        String name = prefs.getString("name","");
        etHi.setText("Hello "+name);
        String imageURIStr = prefs.getString("PPImageURI","");
*/
        try {

            Bitmap circleBitmap = getCroppedBitmap(UserInfo.profilePicture);
            ivPP.setImageBitmap(circleBitmap);
        } catch(Exception e){
            Log.e(TAG, "Could not find photo " + e);
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r+10, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }


    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            //TODO NAPRAVI DA SLIKA BUDE FIXNE VELIČINE
           try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Crop.getOutput(result));
               ivPP.setImageBitmap(getCroppedBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ivPP.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

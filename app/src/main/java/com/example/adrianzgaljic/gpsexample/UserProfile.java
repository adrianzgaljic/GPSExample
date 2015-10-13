package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by adrianzgaljic on 13/10/15.
 */
public class UserProfile extends Activity {

    // log TAG
    public static final String TAG = "logIspis";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

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
            Bitmap circleBitmap = getCroppedBitmap(bitmap);
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
}

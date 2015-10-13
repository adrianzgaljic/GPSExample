package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


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
    private static final int SELECT_PHOTO = 300;

    // log TAG
    public static final String TAG = "logIspis";
    //iv that shows choosen PP
    private ImageView ivRegisterPP;
    //choosen PP
    private Bitmap bitmap;
    //layout that containt buttons for image editing
    public  LinearLayout layoutRotation;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnChoosePP = (Button) findViewById(R.id.btnChoosePP);
        Button btnRotateLeft = (Button) findViewById(R.id.btnRotateLeft);
        Button btnRotateRight = (Button) findViewById(R.id.btnRotateRight);
        ivRegisterPP = (ImageView) findViewById(R.id.ivRegisterPP);
        layoutRotation= (LinearLayout) findViewById(R.id.layoutRotate);


        btnChoosePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
*/
                Crop.pickImage(RegisterActivity.this);
            }
        });

        btnRotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ivRegisterPP.setImageBitmap(bitmap);
            }
        });
        btnRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ivRegisterPP.setImageBitmap(bitmap);
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
                String userID = createUserID();

                SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name",name);
                editor.putString("surname",surname);
                editor.putString("ID",userID);
                editor.putString("created", "created");
                editor.apply();
                Intent intent = new Intent(RegisterActivity.this,UserProfile.class);
                startActivity(intent);



            }
        });



    }



    private String createUserID() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
        layoutRotation.setVisibility(View.VISIBLE);

    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivRegisterPP.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

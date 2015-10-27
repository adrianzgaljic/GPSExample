package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    String action;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile);

        Intent intent = getIntent();

        action = intent.getStringExtra("action");
        user = intent.getStringExtra("user");
        color = intent.getIntExtra("color",Color.YELLOW);





        TextView etHi = (TextView) findViewById(R.id.tvUsernameFriend);
        etHi.setText(user);
        ivPP = (ImageView) findViewById(R.id.ivPPFriend);


        final Button btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
        final Button btnReject = (Button) findViewById(R.id.btnReject);

        if (action.equals("add")){
            btnAddFriend.setText("Add friend");
            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = "http://192.168.5.93:8080/android_connect/send_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    Toast.makeText(FriendProfile.this,"Friend request sent",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (action.equals("accept")){
            btnReject.setVisibility(View.VISIBLE);
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAddFriend.setVisibility(View.INVISIBLE);
                    btnReject.setVisibility(View.INVISIBLE);
                    String link = "http://192.168.5.93:8080/android_connect/delete_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    UserInfo.getFriendRequests().remove(user);
                    Toast.makeText(FriendProfile.this,"User rejected",Toast.LENGTH_SHORT).show();
                }
            });
            btnAddFriend.setText("Accept friend request");
            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = "http://192.168.5.93:8080/android_connect/make_friends.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    link = "http://192.168.5.93:8080/android_connect/delete_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    UserInfo.getFriendRequests().remove(user);
                    btnAddFriend.setVisibility(View.INVISIBLE);
                    btnReject.setVisibility(View.INVISIBLE);
                    UserInfo.getFriends().add(user);
                    Toast.makeText(FriendProfile.this,"You and "+user+" are now friends",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (action.equals("friends")){
            btnAddFriend.setVisibility(View.INVISIBLE);
        }


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

package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adrianzgaljic on 13/10/15.
 */
public class FriendProfile extends AppCompatActivity {


    // log TAG
    public static final String TAG = "logIspis";
    private ImageView ivPoint;
    private ImageView ivPP;
    private int color = 0;
    private String user;
    private String action;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private String address="http://192.168.5.84:80";





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpNavigationDrawer();

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
            btnReject.setVisibility(View.INVISIBLE);
            btnAddFriend.setText("Add friend");
            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = address+"/android_connect/send_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    UserInfo.getSentRequests().add(user);
                    Toast.makeText(FriendProfile.this,"Friend request sent",Toast.LENGTH_SHORT).show();
                    btnAddFriend.setVisibility(View.INVISIBLE);
                    btnReject.setText("Remove friend request");
                    btnReject.setVisibility(View.VISIBLE);
                    btnReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnAddFriend.setVisibility(View.INVISIBLE);
                            btnReject.setVisibility(View.INVISIBLE);
                            //TODO
                            String link = address+"/android_connect/delete_sent_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                            new DBCreateUser(link).execute();
                            UserInfo.getSentRequests().remove(user);
                            Toast.makeText(FriendProfile.this,"Friend request removed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (action.equals("sent")){
            btnReject.setVisibility(View.VISIBLE);
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAddFriend.setVisibility(View.INVISIBLE);
                    btnReject.setVisibility(View.INVISIBLE);
                    String link = address+"/android_connect/android_connect/delete_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    UserInfo.getFriendRequests().remove(user);
                    UserInfo.getRejectedFriends().add(user);
                    Toast.makeText(FriendProfile.this,"User rejected",Toast.LENGTH_SHORT).show();
                }
            });
            btnAddFriend.setText("Accept friend request");
            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = address+"/android_connect/make_friends.php?user="+UserInfo.getUsername()+"&friend="+user;
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
            btnReject.setVisibility(View.VISIBLE);
            btnReject.setText("Remove from friends");
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnReject.setVisibility(View.INVISIBLE);
                    //TODO
                    String link = address+"/android_connect/end_frendship.php?user=" + UserInfo.getUsername() + "&friend=" + user;
                    new DBCreateUser(link).execute();
                    UserInfo.getFriends().remove(user);
                    Toast.makeText(FriendProfile.this, "Friend removed", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (action.equals("yousent")){
            btnAddFriend.setVisibility(View.INVISIBLE);
            btnReject.setText("Remove friend request");
            btnReject.setVisibility(View.VISIBLE);
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAddFriend.setVisibility(View.INVISIBLE);
                    btnReject.setVisibility(View.INVISIBLE);
                    //TODO
                    String link = address+"/android_connect/delete_sent_request.php?user="+UserInfo.getUsername()+"&friend="+user;
                    new DBCreateUser(link).execute();
                    UserInfo.getSentRequests().remove(user);
                    Toast.makeText(FriendProfile.this,"Friend request removed",Toast.LENGTH_SHORT).show();
                }
            });
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

    private void setUpNavigationDrawer() {

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(drawerToggle);


        // Find our drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        MenuItem item = nvDrawer.getMenu().getItem(0);
        item.setTitle(UserInfo.getUsername() + "'s profile");

        int numberOfRequests = UserInfo.getFriendRequests().size();
        String requestsStr;
        if (numberOfRequests == 0){
            requestsStr = "";
        } else {
            requestsStr = "+ "+Integer.toString(numberOfRequests);
        }
        item = nvDrawer.getMenu().getItem(2);
        item.setTitle("Friend requests " + requestsStr);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                fragmentClass = UserProfile.class;
                break;
            case R.id.nav_find_friends:
                fragmentClass = FindFriends.class;
                break;
            case R.id.nav_find_requests:
                fragmentClass = FriendRequestsActivity.class;
                break;
            case R.id.nav_friends:
                fragmentClass = MyFriendsActivity.class;
                break;
            case R.id.nav_map:
                fragmentClass = MapsActivity.class;
                break;
            case R.id.nav_logout:
                logOut();
                fragmentClass = MainActivity.class;
                break;

        }


        menuItem.setChecked(true);
        //setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
        Intent stopwatchIntent = new Intent(FriendProfile.this, fragmentClass);
        startActivity(stopwatchIntent);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    public void logOut() {
        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        editor.apply();
    }




}

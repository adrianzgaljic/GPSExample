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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adrianzgaljic on 20/10/15.
 */
public class MyFriendsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    public static final String TAG = "logIspis";
    private ListView listView;
    private ArrayList<String> array;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        setUpNavigationDrawer();

        array = new ArrayList<String>();
        array = UserInfo.getFriends();


        listView = (ListView) findViewById(R.id.lvFriends);
        FriendListAdapter friendListAdapter = new FriendListAdapter(array,this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(friendListAdapter);
        //listView.setAdapter(adapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) adapter.getItem(position);
                String link = "http://192.168.5.93:8080/android_connect/get_color.php?user=" + selectedValue;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult() == null) ;
                Intent intent = new Intent(MyFriendsActivity.this, FriendProfile.class);
                intent.putExtra("user", selectedValue);
                if (checkUser.getResult().equals("red")) {
                    intent.putExtra("color", Color.RED);
                } else if (checkUser.getResult().equals("green")) {
                    intent.putExtra("color", Color.GREEN);
                } else if (checkUser.getResult().equals("yellow")) {
                    intent.putExtra("color", Color.YELLOW);
                } else {
                    intent.putExtra("color", Color.BLUE);
                }
                Toast.makeText(MyFriendsActivity.this, selectedValue, Toast.LENGTH_SHORT).show();
                intent.putExtra("action", "friends");
                startActivity(intent);
            }
        });*/
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
        Intent stopwatchIntent = new Intent(MyFriendsActivity.this, fragmentClass);
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

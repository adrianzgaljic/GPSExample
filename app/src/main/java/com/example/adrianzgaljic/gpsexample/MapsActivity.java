package com.example.adrianzgaljic.gpsexample;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    Marker myMarker;
    BitmapDescriptor myIcon;
    int myColor;
    LatLng myLatLng;
    DrawerLayout mDrawer;
    ArrayList<UserLocation> friendLocations = new ArrayList<UserLocation>();
    Map<String, Marker> friendsMarkers = new HashMap<String, Marker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        MenuItem item = nvDrawer.getMenu().getItem(0);
        item.setTitle(UserInfo.getUsername() + "'s profile");

        int numberOfRequests = UserInfo.getFriendRequests().size();
        item = nvDrawer.getMenu().getItem(2);
        item.setTitle("Friend requests +" + numberOfRequests);
        /*
        setMyIconColor();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawFriendsOnMap();
            }
        });
*/


    }

    private void setMyIconColor() {

        Bitmap imageBitmap;
        if (UserInfo.getColor() == Color.RED) {
            imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                    R.drawable.circle_red);
            myColor = Color.RED;
        } else if (UserInfo.getColor() == Color.GREEN){
            imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                    R.drawable.circle_green);
            myColor = Color.GREEN;
        } else if (UserInfo.getColor() == Color.YELLOW) {
            imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                    R.drawable.circle_yellow);
            myColor = Color.YELLOW;
        } else {
            imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                    R.drawable.circle_blue);
            myColor = Color.BLUE;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 20, 20, false);
        myIcon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        try {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }catch(Error e){

        }

        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        //drawFriendsOnMap();


        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition pos) {



                if(myMarker!=null){
                    myMarker.remove();
                    if (myColor != UserInfo.getColor()){
                        setMyIconColor();
                    }
                }

                myMarker = mMap.addMarker(new MarkerOptions()
                        .position(myLatLng)
                        .title(UserInfo.getUsername())
                        .icon(myIcon));


            }
        });
    }

    private void drawFriendsOnMap() {
        try {
            friendLocations = LogInActivity.getFriendsLocations(UserInfo.getUsername());
        }catch (Exception e){

        }

        for (UserLocation friend: friendLocations) {
            String link = "http://192.168.5.93:8080/android_connect/get_color.php?user=" + friend.getUsername();
            DBCheckUser checkUser = new DBCheckUser(link);
            checkUser.execute();
            while (checkUser.getResult() == null) ;


            BitmapDescriptor icon;
            Bitmap imageBitmap;
            if (checkUser.getResult().equals("red")) {
                imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                        R.drawable.circle_red);
            } else if (checkUser.getResult().equals("green")) {
                imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                        R.drawable.circle_green);
            } else if (checkUser.getResult().equals("yellow")) {
                imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                        R.drawable.circle_yellow);
            } else {
                imageBitmap = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                        R.drawable.circle_blue);
            }
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 20, 20, false);
            icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);
            LatLng latLng = new LatLng(friend.getLatitude(), friend.getLongitude());

            if(friendsMarkers.containsKey(friend.getUsername())){
                friendsMarkers.get(friend.getUsername()).remove();
            }
            Marker friendMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(friend.getUsername())
                    .icon(icon));
            friendsMarkers.put(friend.getUsername(),friendMarker);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        TextView locationTv = (TextView) findViewById(R.id.tvLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        myLatLng = new LatLng(latitude, longitude);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        DBUpdatePosition updateUser = new DBUpdatePosition(UserInfo.getUsername(),longitude,latitude);
        updateUser.execute();

    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = UserProfile.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FindFriends.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = FriendRequestsActivity.class;
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = MyFriendsActivity.class;
                break;
            case R.id.nav_logout:
                logOut();
                fragmentClass = MainActivity.class;
                break;

        }


        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
        Intent stopwatchIntent = new Intent(MapsActivity.this, fragmentClass);
        startActivity(stopwatchIntent);
    }

    public void logOut(){
        SharedPreferences prefs = getSharedPreferences("GPSExample", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username","");
        editor.apply();
    }


}

package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adrianzgaljic on 19/10/15.
 */
public class FindFriends extends Activity {


    ArrayAdapter<String> adapter;
    public static final String TAG = "logIspis";
    ListView  listView;
    ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        final TextView tvResult = (TextView) findViewById(R.id.tvResults);

        array = new ArrayList<String>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) adapter.getItem(position);
                Toast.makeText(FindFriends.this, selectedValue, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FindFriends.this, FriendProfile.class);
                intent.putExtra("user", selectedValue);
                intent.putExtra("color", Color.YELLOW);
                startActivity(intent);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString();
                if (query.equals("")) {
                    Toast.makeText(FindFriends.this, "Empty input", Toast.LENGTH_SHORT);
                } else {
                    String link = "http://192.168.5.93:8080/android_connect/find_users.php?user=" + query;
                    DBCheckUser checkUser = new DBCheckUser(link);
                    checkUser.execute();
                    while (checkUser.getResult() == null) ;
                    tvResult.setText(checkUser.getResult());
                    array.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));
                    adapter.notifyDataSetChanged();




                }
            }
        });




    }

}

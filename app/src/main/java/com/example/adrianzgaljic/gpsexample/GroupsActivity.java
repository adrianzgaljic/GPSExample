package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.util.ArrayList;

/**
 * Created by adrianzgaljic on 07/11/15.
 */
public class GroupsActivity extends Activity {

    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<String> groupNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        initializeList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddGroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });
    }

    private void initializeList() {
        groups = UserInfo.getGroups();
        for (Group group: groups){
            groupNames.add(group.getName());
        }

        GroupListAdapter groupListAdapter = new GroupListAdapter(groupNames,this);
        ListView listView = (ListView) findViewById(R.id.lvGroups);
        listView.setAdapter(groupListAdapter);

        ArrayAdapter adapterRecived = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupNames);
        adapterRecived.setNotifyOnChange(true);
        listView.setAdapter(adapterRecived);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedValue = (String) adapterRecived.getItem(position);

            }
        });
    }

    private void showDialog() {
        final EditText etGroupName = new EditText(this);

// Set the default text to a link of the Queen
        etGroupName.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Create group")
                .setMessage("Choose name for the group")
                .setView(etGroupName)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = etGroupName.getText().toString();
                        createGroup(name);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    private void createGroup(String name) {
        Group group = new Group(name);
        UserInfo.getGroups().add(group);
        groupNames.add(name);
    }
}

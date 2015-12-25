package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;

public class FriendListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public FriendListAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.friend_list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedValue = list.get(position);
                String link = Configuration.address+"get_color.php?user=" + selectedValue;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult() == null) ;
                Intent intent = new Intent(context, FriendProfile.class);
                intent.putExtra("user", list.get(position));
                if (checkUser.getResult().equals("red")) {
                    intent.putExtra("color", Color.RED);
                } else if (checkUser.getResult().equals("green")) {
                    intent.putExtra("color", Color.GREEN);
                } else if (checkUser.getResult().equals("yellow")) {
                    intent.putExtra("color", Color.YELLOW);
                } else {
                    intent.putExtra("color", Color.BLUE);
                }

                if (UserInfo.getFriends().contains(selectedValue)){
                    //selected user is allready in friend list
                    intent.putExtra("action", "friends");
                } else if (UserInfo.getSentRequests().contains(selectedValue)){
                    //you allready sent friend request
                    intent.putExtra("action", "yousent");
                } else if (UserInfo.getRejectedFriends().contains(selectedValue)){
                    intent.putExtra("action", "rejected");
                } else if (UserInfo.getFriendRequests().contains(selectedValue)) {
                    intent.putExtra("action", "sent");
                } else {
                    intent.putExtra("action", "add");
                }
                context.startActivity(intent);
            }
        });

        final Switch swtchFriend = (Switch)view.findViewById(R.id.swtchFriends);
        boolean isAllowed;
        ArrayList<String> allowedFriends = getEnabledFriends(UserInfo.getUsername());
        if (allowedFriends.contains(list.get(position))){
            isAllowed = true;
        } else {
            isAllowed = false;
        }
        swtchFriend.setChecked(isAllowed);
        Log.i(MapsActivity.TAG, "POSITION..." + list.get(position));
        for (String friend:allowedFriends){
            Log.i(MapsActivity.TAG,"FRIEND..."+friend);
        }

        swtchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MapsActivity.TAG, "lista..." + list.get(position) + " " + swtchFriend.isChecked());
                if (swtchFriend.isChecked()) {
                    String link = Configuration.address+"add_permission.php?user=" + UserInfo.getUsername() + "&friend=" + list.get(position);
                    new DBCreateUser(link).execute();

                } else {
                    String link = Configuration.address+"remove_permission.php?user=" + UserInfo.getUsername() + "&friend=" + list.get(position);
                    new DBCreateUser(link).execute();
                }
            }
        });

        //Handle buttons and add onClickListeners
      /*  Button deleteBtn = (Button)view.findViewById(R.id.btnHide);
        Button addBtn = (Button)view.findViewById(R.id.btnShow);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });
*/
        return view;
    }

    public static ArrayList<String> getEnabledFriends(String username) {

        ArrayList<String> friends = new ArrayList<>();
        String link = Configuration.address+"get_allowed_friends.php?user=" + username;
        DBCheckUser checkUser = new DBCheckUser(link);
        checkUser.execute();
        while (checkUser.getResult() == null) ;
        Log.i(MapsActivity.TAG,"rasultresult="+checkUser.getResult());

        friends.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));

        return friends;
    }
}
package com.rainbowsix.careerpass;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lihan on 10/9/2017.
 */

public class ListAdapter extends ArrayAdapter {
    List<ListSingle> list;
    HashMap<Integer,Boolean> isSelected;
    boolean hide;
    private DatabaseReference mDatabase;
    private final Context context;
    public ListAdapter(Context context, List<ListSingle> objects) {

        super(context, 0, objects);
        this.context = context;
        list = new ArrayList<>();
        this.list = objects;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        final ListSingle single = list.get(position);

        TextView tag = (TextView)view.findViewById(R.id.tag);
        TextView time = (TextView)view.findViewById(R.id.time);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox);
        tag.setText(single.getTag());
        time.setText("Created: " + single.getTime() + " | " + single.getCat());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                final boolean checked = ischecked;
                SharedPreferences sharedPre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                final String email = sharedPre.getString("username","");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.child("User");
                        String name = "aaa";
                        for(DataSnapshot singleUser : snap.getChildren()){
                            String getEmail = singleUser.child("email").getValue().toString();
                            if (getEmail.equals(email)){
                                name = singleUser.getKey().toString();
                            }
                        }
                        if (checked) {
                            single.setChecked(true);
                            mDatabase.child("User").child(name).child("todo").child(single.getTag()).child("complete").setValue("true");
                        } else {
                            single.setChecked(false);
                            mDatabase.child("User").child(name).child("todo").child(single.getTag()).child("complete").setValue("false");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        checkBox.setChecked(single.getChecked());
        return view;
    }
}

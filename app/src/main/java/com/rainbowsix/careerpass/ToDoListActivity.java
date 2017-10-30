package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToDoListActivity extends MenuActivity{
    ListView list;
    ListAdapter listAdapter;
    List<ListSingle> data;
    List<ListSingle> notselected;
    Button complete;
    boolean hide;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_to_do_list, frameLayout);
        setContent();
    }

    public void setContent() {
        complete = (Button)findViewById(R.id.show_complete);
        hide = true;
        list = (ListView)findViewById(R.id.list_todo);
        data = new ArrayList<ListSingle>();
        notselected = new ArrayList<ListSingle>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot userSnapshot = dataSnapshot.child("aaa");
                for (DataSnapshot block : userSnapshot.getChildren()) {
                    toDoListBlock cur1 = block.getValue(toDoListBlock.class);
                    ListSingle temp = new ListSingle(cur1.name, cur1.category, cur1.time, Boolean.parseBoolean(cur1.complete));
                    if(!Boolean.parseBoolean(cur1.getComplete())) {
                        notselected.add(temp);
                    }
                    data.add(temp);
                    listAdapter.notifyDataSetChanged();
                    Log.v("boolean set: ", cur1.complete);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).getChecked())
                if(!notselected.contains(data.get(i)))
                    notselected.add(data.get(i));
        }
        listAdapter = new ListAdapter(ToDoListActivity.this, notselected);
        list.setAdapter(listAdapter);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getChecked())
                            if(!notselected.contains(data.get(i)))
                                notselected.add(data.get(i));
                    }
                    complete.setText("Hide complete");
                    hide = false;
                    listAdapter.notifyDataSetChanged();
                }
                else {
                    Iterator it = notselected.iterator();
                    while (it.hasNext()) {
                        ListSingle temp = (ListSingle) it.next();
                        if (temp.getChecked())
                            it.remove();
                    }
                    complete.setText("Show complete");
                    hide = true;
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(2).setChecked(true);
    }


}

package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToDoListActivity extends MainActivity {
    ListView list;
    ListAdapter listAdapter;
    List<ListSingle> data;
    List<ListSingle> completed;
    Button complete, setComplete;
    boolean hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_to_do_list, frameLayout);
        setContent();
    }

    public void setContent() {
        complete = (Button)findViewById(R.id.show_complete);
        setComplete = (Button)findViewById(R.id.set_complete);
        hide = true;
        list = (ListView)findViewById(R.id.list_todo);
        data = new ArrayList<ListSingle>();
        completed = new ArrayList<ListSingle>();
        data.add(new ListSingle("Prepare for oci", "October 8, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        data.add(new ListSingle("Prepare for oci3", "October 28, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        data.add(new ListSingle("Prepare for oci", "October 8, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        data.add(new ListSingle("Prepare for oci3", "October 28, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        data.add(new ListSingle("Prepare for oci", "October 8, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        data.add(new ListSingle("Prepare for oci3", "October 28, 2016", "Interview", false));
        data.add(new ListSingle("Prepare for oci2", "October 18, 2016", "Interview", true));
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).getChecked()) completed.add(data.get(i));
        }
        listAdapter = new ListAdapter(ToDoListActivity.this, completed);
        list.setAdapter(listAdapter);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide == true) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getChecked()) completed.add(data.get(i));
                    }
                    complete.setText("Hide complete");
                    hide = false;
                    listAdapter.notifyDataSetChanged();
                }
                else {
                    Iterator it = completed.iterator();
                    while (it.hasNext()) {
                        ListSingle temp = (ListSingle) it.next();
                        if (temp.getChecked()) it.remove();
                    }
                    complete.setText("Show complete");
                    hide = true;
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        setComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getChecked()) completed.add(data.get(i));
                    else if (completed.contains(data.get(i))) completed.remove(data.get(i));
                }
                listAdapter.notifyDataSetChanged();
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

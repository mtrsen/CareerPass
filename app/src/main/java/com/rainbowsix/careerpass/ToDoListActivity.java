package com.rainbowsix.careerpass;

import android.os.Bundle;
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
    List<ListSingle> uncompleted;
    Button complete;
    boolean hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_to_do_list, frameLayout);
        setContent();
    }

    public void setContent() {
        complete = (Button)findViewById(R.id.show_complete);
        hide = true;
        list = (ListView)findViewById(R.id.list_todo);
        data = new ArrayList<ListSingle>();
        uncompleted = new ArrayList<ListSingle>();
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
            if (!data.get(i).getChecked()) uncompleted.add(data.get(i));
        }
        listAdapter = new ListAdapter(ToDoListActivity.this, uncompleted);
        list.setAdapter(listAdapter);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide == true) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getChecked()) uncompleted.add(data.get(i));
                    }
                    complete.setText("Hide complete");
                    hide = false;
                    listAdapter.notifyDataSetChanged();
                }
                else {
                    Iterator it = uncompleted.iterator();
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(2).setChecked(true);
    }


}

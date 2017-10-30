package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends MenuActivity {
    List<ListSingle> data;
    SearchView searchView;
    ListView listView;
    SearchAdapter searchAdapter;
    Button search;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);
        initialize();
    }

    public void initialize() {
        data = new ArrayList<>();
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.listview);
        searchAdapter = new SearchAdapter(SearchActivity.this, data);
        search = (Button)findViewById(R.id.search);

        listView.setAdapter(searchAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.add(new ListSingle("Prepare for interviews", "20170303", "Interview", false));
                data.add(new ListSingle("Participate interview questions", "20180303", "Resume", false));
                data.add(new ListSingle("Participate in oncampus interviews", "20190303", "Interview", false));
                searchAdapter.notifyDataSetChanged();
            }
        });
    }



}

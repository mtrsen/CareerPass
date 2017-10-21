package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends MainActivity {
    List<ListSingle> data;
    SearchView searchView;
    ListView listView;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);
        initialize();
    }

    public void initialize() {
        data = new ArrayList<>();
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.listview);
        searchAdapter = new SearchAdapter(SearchActivity.this, data);
        data.add(new ListSingle("prepare1", "20170303", "Interview", false));
        data.add(new ListSingle("prepare2", "20180303", "Resume", false));
        data.add(new ListSingle("prepare3", "20190303", "Interview", false));
        data.add(new ListSingle("prepare4", "20190403", "Others", false));
        listView.setAdapter(searchAdapter);
    }



}

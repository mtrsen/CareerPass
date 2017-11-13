package com.rainbowsix.careerpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchActivity extends MenuActivity {
    List<ListSingle> data;
    HashSet<String> visited;
    SearchView searchView;
    ListView listView;
    SearchAdapter searchAdapter;
    Button search;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);
        initialize();
    }

    public void initialize() {
        data = new ArrayList<>();
        visited = new HashSet<>();
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.listview);
        searchAdapter = new SearchAdapter(SearchActivity.this, data);
        search = (Button)findViewById(R.id.search);

        listView.setAdapter(searchAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.child("post");
                        for(DataSnapshot post : snap.getChildren()){
                            String date = post.getKey().toString();
                            String query = searchView.getQuery().toString();
                            if(post.hasChild("interview")){
                                for(DataSnapshot single :  post.child("interview").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag) && !visited.contains(tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"interview",Boolean.parseBoolean(add)));
                                        visited.add(tag);
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("job search")){
                                for(DataSnapshot single :  post.child("job search").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag) && !visited.contains(tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"job search",Boolean.parseBoolean(add)));
                                        visited.add(tag);
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("resume")){
                                for(DataSnapshot single :  post.child("resume").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag) && !visited.contains(tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"resume",Boolean.parseBoolean(add)));
                                        visited.add(tag);
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("others")){
                                for(DataSnapshot single :  post.child("others").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag) && !visited.contains(tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"others",Boolean.parseBoolean(add)));
                                        visited.add(tag);
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                searchAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                intent.putExtra("tag", searchAdapter.list.get(i).getTag());
                startActivity(intent);
            }
        });
    }
    public boolean contains(String query, String tag) {
        if (query.equals("")) return true;
        int size = query.length();
        for (int i = 0; i <= tag.length() - size; i++) {
            if (tag.substring(i, i + size).toLowerCase().equals(query.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

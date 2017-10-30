package com.rainbowsix.careerpass;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 98fe2630f2444cbe1aed49de87471129e4682165
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

<<<<<<< HEAD
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
=======
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> 98fe2630f2444cbe1aed49de87471129e4682165

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends MenuActivity {
    List<ListSingle> data;
    SearchView searchView;
    ListView listView;
    SearchAdapter searchAdapter;
    Button search;
<<<<<<< HEAD
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
=======
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
>>>>>>> 98fe2630f2444cbe1aed49de87471129e4682165
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
                data.clear();
<<<<<<< HEAD

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
                                    if(contains(query,tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"interview",Boolean.parseBoolean(add)));
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("job search")){
                                for(DataSnapshot single :  post.child("job search").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"job search",Boolean.parseBoolean(add)));
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("resume")){
                                for(DataSnapshot single :  post.child("resume").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"resume",Boolean.parseBoolean(add)));
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if(post.hasChild("others")){
                                for(DataSnapshot single :  post.child("others").child("tag").getChildren()) {
                                    String tag = single.child("tag").getValue().toString();
                                    if(contains(query,tag)){
                                        String add = single.child("add").getValue().toString();
                                        data.add(new ListSingle(tag,date,"others",Boolean.parseBoolean(add)));
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
    }
    public boolean contains(String query, String tag){
        if(query.equals(""))    return true;
        int size = query.length();
        for(int i = 0; i <= tag.length() - size; i++){
            if(tag.substring(i, i + size).toLowerCase().equals(query.toLowerCase())){
                return true;
            }
        }
        return false;
=======
                data.add(new ListSingle("Prepare for interviews", "20170303", "Interview", false));
                data.add(new ListSingle("Participate interview questions", "20180303", "Resume", false));
                data.add(new ListSingle("Participate in oncampus interviews", "20190303", "Interview", false));
                searchAdapter.notifyDataSetChanged();
            }
        });
>>>>>>> 98fe2630f2444cbe1aed49de87471129e4682165
    }



}

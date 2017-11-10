package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Combo implements Comparable<Combo> {
    int num;
    String time;
    public Combo(int num, String time) {
        this.num = num;
        this.time = time;
    }

    @Override
    public int compareTo(Combo combo) {
        return Integer.parseInt(time) - Integer.parseInt(combo.time);
    }
}
public class GraphActivity extends AppCompatActivity {
    List<Combo> tagData;
    GraphView graph;
    Button back;
    String tagName;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        tagData = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            tagName = extras.getString("tag");
            Log.v("tagname", tagName);
        }

        //back = (Button)findViewById(R.id.back);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.child("post");
                for(DataSnapshot date : snap.getChildren()){
                    String time = date.getKey().toString();
                    for(DataSnapshot category : date.getChildren()) {
                        for(DataSnapshot tag : category.child("tag").getChildren()) {
                            String cur = tag.child("tag").getValue().toString();
                            if(cur.equals(tagName)) {
                                int num = Integer.parseInt(tag.child("count").getValue().toString());
                                Combo temp = new Combo(num, time);
                                tagData.add(temp);
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Collections.sort(tagData);
        List<Combo> result = new ArrayList<>();
        int ind = 0;
        for(int i = 0; i < tagData.size(); i++) {
            int temp = Integer.parseInt(tagData.get(i).time) / 100;
            if(i == 0) {
                Combo comp = new Combo(tagData.get(i).num, Integer.toString(temp));
                result.add(comp);
            } else {
                if(temp == Integer.parseInt(result.get(ind).time)) {
                    result.get(ind).num += tagData.get(i).num;
                } else {
                    Combo comp = new Combo(tagData.get(i).num, Integer.toString(temp));
                    result.add(comp);
                    ind++;
                }
            }
        }
        
        graph = (GraphView)findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        //series.setTitle("Time trend");
        graph.addSeries(series);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

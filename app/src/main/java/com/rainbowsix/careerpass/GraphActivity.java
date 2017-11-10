package com.rainbowsix.careerpass;

import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Combo{
    int num;
    String time;
    public Combo(int num, String time) {
        this.num = num;
        this.time = time;
    }
}
public class GraphActivity extends AppCompatActivity {
    List<Combo> tagData;
    GraphView graph;
    Button back;
    String tagName;
    DatabaseReference databaseReference;
    Map<String, Combo> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        tagData = new ArrayList<>();
        map = new HashMap<>();
        back = (Button)findViewById(R.id.back);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.child("post");
                for(DataSnapshot post : snap.getChildren()){
                    for(DataSnapshot category : post.getChildren()) {
                        for(DataSnapshot tag : category.getChildren()) {

                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String value = extras.getString("tag");
            Log.v("tagname", value);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

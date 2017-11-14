package com.rainbowsix.careerpass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
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
public class GraphActivity extends MenuActivity {
    List<Combo> tagData;
    ArrayList<String> result;
    Button back;
    String tagName;
    GridView related;
    DatabaseReference databaseReference;
    TextView currenttag;
    GridviewAdapter gadapter;
    String[] month = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_graph, frameLayout);
        //setContentView(R.layout.activity_graph);
        tagData = new ArrayList<>();
        result = new ArrayList<>();
        gadapter = new GridviewAdapter(getApplicationContext(), result);
        related = (GridView)findViewById(R.id.related);
        currenttag = (TextView)findViewById(R.id.currenttag);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            tagName = extras.getString("tag");
            currenttag.setText(tagName);
            result = (ArrayList<String>)extras.getSerializable("result");
            result.remove(tagName);
            //Log.v("tagname", tagName);
//            for (int i = 0; i < result.size(); i++) {
//                Log.v("result", result.get(i));
//            }
            gadapter = new GridviewAdapter(getApplicationContext(), result);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_expandable_list_item_1,
//                    result);
            related.setAdapter(gadapter);
            gadapter.notifyDataSetChanged();
        }
        final GraphView graph = (GraphView)findViewById(R.id.graph);
        back = (Button)findViewById(R.id.back);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.child("post");
                for(DataSnapshot date : snap.getChildren()){
                    String time = date.getKey().toString().substring(4);

                    for(DataSnapshot category : date.getChildren()) {
                        for(DataSnapshot tag : category.child("tag").getChildren()) {
                            String cur = tag.child("tag").getValue().toString();

                            if(cur.equals(tagName)) {

                                int num = Integer.parseInt(tag.child("count").getValue().toString());
                                Combo temp = new Combo(num, time);
                                tagData.add(temp);
                                //Log.v("checktime", Integer.toString(tagData.size()));
                                break;
                            }
                        }
                    }
                }
                //Log.v("checktime222", Integer.toString(tagData.size()));
                //Log.v("checktimeint", tagData.get(0).time);
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



                //Log.v("createtime", "" + tagData.size());
                  DataPoint[] data = new DataPoint[result.size()];
//                Date small = null;
//                Date large = null;
                  int countsmall = Integer.MAX_VALUE;
                  int countlarge = Integer.MIN_VALUE;
                for (int i = 0; i < data.length; i++) {
                    Combo point = result.get(i);
//                    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM");
//                    String temp = point.time.substring(0, 4) + "-" + point.time.substring(4);
//                    Date t;
//                    try {
//                        t = ft.parse(temp);
//                    } catch (ParseException e) {
//                        t = null;
//                    }
//                    if (i == 0) small = t;
//                    if (i == data.length - 1) large = t;
                    data[i] = new DataPoint(Integer.parseInt(point.time), point.num);
                    countsmall = Math.min(countsmall, point.num);
                    countlarge = Math.max(countlarge, point.num);
                    //Log.v("createtime", "" + Integer.parseInt(point.time));
                }


                //series.setBackgroundColor(20);
                draw(graph, data, countsmall, countlarge);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagData.clear();
                //graph.destroyDrawingCache();
                finish();
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);

            }
        });
    }

    public void draw(GraphView graph, DataPoint[] data, int countsmall, int countlarge) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

        //series.setTitle("Time trend");
        //graph.getViewport().setScrollable(true); // enables horizontal scrolling
        //graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GraphActivity.this));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(12); // only 4 because of the space
        //graph.getGridLabelRenderer().setNumVerticalLabels(6);

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(countsmall);
        graph.getViewport().setMaxY(countlarge);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        //graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        //graph.getGridLabelRenderer().setVerticalAxisTitle("Post count");
        graph.getGridLabelRenderer().setTextSize(30);
        //graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(30);
        //graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(30);
        //graph.getGridLabelRenderer().setLabelsSpace(10);
//
//                graph.getViewport().setMinX(Integer.parseInt(result.get(0).time));
//                graph.getViewport().setMaxX(Integer.parseInt(result.get(result.size() - 1).time));
//                graph.getViewport().setXAxisBoundsManual(true);
//
        //graph.setTitle("Time Trend");

                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            String prev = super.formatLabel(value, isValueX);
                            //Log.v("xxx", prev);
                            if (prev.indexOf(".") != -1) prev = prev.substring(0, prev.indexOf("."));
                            int m = Integer.parseInt(prev);
                            //String year = prev.substring(0, 3) + prev.charAt(4);
                            //Log.v("currentmonth", "" + m);
                            return month[m];
                            //return prev;
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, isValueX);
                        }
                    }
                });

        graph.addSeries(series);
        series.setDrawBackground(true);
        //Log.v("nexttime", "" + result.size());
        series.setBackgroundColor(Color.argb(255, 255, 175, 64));
    }


}

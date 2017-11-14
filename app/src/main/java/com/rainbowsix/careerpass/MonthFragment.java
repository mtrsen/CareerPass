package com.rainbowsix.careerpass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by lihan on 10/13/2017.
 */

public class MonthFragment extends Fragment {
    View rootView1;
    GridView list_interview, list_resume, list_xxx, list_others;
    ImageView right, left;
    TextView Month, year;
    TagAdapter listAdapter_interview, listAdapter_resume, listAdapter_xxx, listAdapter_others;
    List<TagSingle> data_interview;
    List<TagSingle> data_resume;
    List<TagSingle> data_xxx;
    List<TagSingle> data_others;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    Date currentTime = Calendar.getInstance().getTime();
    String[] s = currentTime.toString().split(" ");
    String curMon = s[1];
    int curYear = Integer.parseInt(s[5]);


    //current date to add to to do list
    Date nowTime = Calendar.getInstance().getTime();
    String[] now = nowTime.toString().split(" ");
    String nowMon = now[1];
    int nowDate = Integer.parseInt(now[2]);
    int nowYear = Integer.parseInt(now[5]);

    Button addtolist, addpost;
    ScrollView scrollView;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mFirebaseDatabaseReference;

    String name ;

    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView1 = inflater.inflate(R.layout.month, container, false);
        initialize();

        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.USER_NAME, Context.MODE_PRIVATE);
        name = "aaa";

        listAdapter_interview = new TagAdapter(getContext(), data_interview);
        list_interview.setAdapter(listAdapter_interview);
        listAdapter_resume = new TagAdapter(getContext(), data_resume);
        list_resume.setAdapter(listAdapter_resume);
        listAdapter_xxx = new TagAdapter(getContext(), data_xxx);
        list_xxx.setAdapter(listAdapter_xxx);
        listAdapter_others = new TagAdapter(getContext(), data_others);
        list_others.setAdapter(listAdapter_others);
        return rootView1;
    }

    public void initialize() {

        //scrollView = (ScrollView)rootView1.findViewById(R.id.scrollview);
        list_interview = (GridView)rootView1.findViewById(R.id.interviewlist);
        list_resume = (GridView)rootView1.findViewById(R.id.resumelist);
        list_xxx = (GridView)rootView1.findViewById(R.id.xxxlist);
        list_others = (GridView)rootView1.findViewById(R.id.otherslist);
        addtolist = (Button)rootView1.findViewById(R.id.addtolist);
        addpost = (Button)rootView1.findViewById(R.id.addpost);
        left = (ImageView)rootView1.findViewById(R.id.left);
        right = (ImageView)rootView1.findViewById(R.id.right);
        year = (TextView)rootView1.findViewById(R.id.year);
        Month = (TextView)rootView1.findViewById(R.id.month);


        data_xxx = new ArrayList<TagSingle>();
        data_resume = new ArrayList<TagSingle>();
        data_interview = new ArrayList<TagSingle>();
        data_others = new ArrayList<TagSingle>();

        Month.setText(curMon);
        year.setText(String.valueOf(curYear));

        //===Read from database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference();
        List<String> dateList = getDateList(nowYear,nowMon);
        setContent(dateList);

        //initialize for current month to add to to do list
        int index = Arrays.asList(month).indexOf(nowMon) + 1;
        String monthCur = index < 10 ? "0" + Integer.toString(index) : Integer.toString(index);
        final String date = Integer.toString(nowYear) + monthCur + (nowDate < 10 ? "0" + nowDate : nowDate);
        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Added Successfully!", Toast.LENGTH_LONG).show();
                for(int i = 0; i < data_interview.size(); i++){
                    if(Boolean.valueOf(data_interview.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("interview",date,data_interview.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child(name).child(data_interview.get(i).getTag()).setValue(todo);
                    }
                }
                for(int i = 0; i < data_resume.size(); i++){
                    if(Boolean.valueOf(data_resume.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("resume",date,data_resume.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child(name).child(data_resume.get(i).getTag()).setValue(todo);

                    }
                }
                for(int i = 0; i < data_xxx.size(); i++){
                    if(Boolean.valueOf(data_xxx.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("xxx",date,data_xxx.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child(name).child(data_xxx.get(i).getTag()).setValue(todo);
                    }
                }
                for(int i = 0; i < data_others.size(); i++){
                    if(Boolean.valueOf(data_others.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("others",date,data_others.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child(name).child(data_others.get(i).getTag()).setValue(todo);
                    }
                }

            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempMon = Arrays.asList(month).indexOf(curMon);
                if (tempMon == 0) {
                    tempMon = 11;
                    curYear--;
                }
                else tempMon--;
                curMon = month[tempMon];
                year.setText(String.valueOf(curYear));
                Month.setText(curMon);

                List<String> dateList = getDateList(curYear,curMon);
                setContent(dateList);

            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempMon = Arrays.asList(month).indexOf(curMon);
                if (tempMon == 11) {
                    tempMon = 0;
                    curYear++;
                }
                else tempMon++;
                curMon = month[tempMon];
                year.setText(String.valueOf(curYear));
                Month.setText(curMon);
                List<String> dateList = getDateList(curYear,curMon);
                setContent(dateList);
            }
        });
    }

    public List<String> getDateList(int yearInput, String monthInput){
        List<String> dateList = new ArrayList<String>();
        int monIndex = Arrays.asList(month).indexOf(monthInput) + 1;
        int lastDay = daysOfMonth[monIndex-1];
        for(int i = 1; i <= lastDay ; i++){
            String dataCur = i < 10 ? "0" + Integer.toString(i) : Integer.toString(i);
            String monthCur = monIndex < 10 ? "0" + Integer.toString(monIndex) : Integer.toString(monIndex);
            String newDate = Integer.toString(yearInput) + monthCur + dataCur;
            dateList.add(newDate);
        }
        return dateList;
    }

    public void setContent(List<String> DateList){
        data_resume.clear();
        data_interview.clear();
        data_others.clear();
        data_xxx.clear();
        TextView myTextView= (TextView) rootView1.findViewById(R.id.resume_percent);
        myTextView.setText("");
        TextView myTextView1= (TextView) rootView1.findViewById(R.id.interview_percent);
        myTextView1.setText("");
        TextView myTextView2= (TextView) rootView1.findViewById(R.id.xxx_percent);
        myTextView2.setText("");
        TextView myTextView3= (TextView) rootView1.findViewById(R.id.others_percent);
        myTextView3.setText("");

        final List<String> dateList = DateList;
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Integer> interMap = new HashMap<String, Integer>();
                HashMap<String, Integer> resumeMap = new HashMap<String, Integer>();
                HashMap<String, Integer> otherMap = new HashMap<String, Integer>();
                HashMap<String, Integer> xxxMap = new HashMap<String, Integer>();

                for(String date : dateList){
                    if(dataSnapshot.child("post").hasChild(date)){
                        if(dataSnapshot.child("post").child(date).hasChild("resume")){
                            DataSnapshot snap = dataSnapshot.child("post").child(date).child("resume").child("tag");

                            for(DataSnapshot post : snap.getChildren()){
                                PostCategory postCategory = post.getValue(PostCategory.class);
                                int value = resumeMap.containsKey(postCategory.getTag()) ? resumeMap.get(postCategory.getTag()): 0;
                                resumeMap.put(postCategory.getTag(),  value+postCategory.getCount());
                            }
                        }
                        if(dataSnapshot.child("post").child(date).hasChild("interview")){

                            DataSnapshot snap1 = dataSnapshot.child("post").child(date).child("interview").child("tag");
                            for(DataSnapshot post : snap1.getChildren()){
                                PostCategory postCategory = post.getValue(PostCategory.class);
                                int value = interMap.containsKey(postCategory.getTag()) ? interMap.get(postCategory.getTag()): 0;
                                interMap.put(postCategory.getTag(),  value+postCategory.getCount());
                            }
                        }
                        if(dataSnapshot.child("post").child(date).hasChild("job search")){
                            DataSnapshot snap2 = dataSnapshot.child("post").child(date).child("job search").child("tag");
                            for(DataSnapshot post : snap2.getChildren()){
                                PostCategory postCategory = post.getValue(PostCategory.class);
                                int value = xxxMap.containsKey(postCategory.getTag()) ? xxxMap.get(postCategory.getTag()): 0;
                                xxxMap.put(postCategory.getTag(),  value+postCategory.getCount());
                            }
                        }
                        if(dataSnapshot.child("post").child(date).hasChild("others")){
                            DataSnapshot snap3 = dataSnapshot.child("post").child(date).child("others").child("tag");
                            for(DataSnapshot post : snap3.getChildren()){
                                PostCategory postCategory = post.getValue(PostCategory.class);
                                int value = otherMap.containsKey(postCategory.getTag()) ? otherMap.get(postCategory.getTag()): 0;
                                otherMap.put(postCategory.getTag(),  value+postCategory.getCount());
                            }
                        }
                    }
                }
                int totalInterview = 0, totalJob = 0, totalResume = 0, totalOther = 0 ;
                for(String key : interMap.keySet()){
                    data_interview.add(new TagSingle(key, interMap.get(key), false));
                    totalInterview += interMap.get(key);
                }
                for(String key : resumeMap.keySet()){
                    data_resume.add(new TagSingle(key, resumeMap.get(key), false));
                    totalResume += resumeMap.get(key);
                }
                for(String key : otherMap.keySet()){
                    data_others.add(new TagSingle(key, otherMap.get(key), false));
                    totalOther += otherMap.get(key);
                }
                for(String key : xxxMap.keySet()){
                    data_xxx.add(new TagSingle(key, xxxMap.get(key), false));
                    totalJob += xxxMap.get(key);
                }
                int total = totalJob + totalInterview + totalResume + totalOther;
                double interRatio = total == 0 ? 0.0 : (double)totalInterview/total * 100;
                double resumeRatio = total == 0 ? 0.0 : (double)totalResume/total * 100;
                double otherRatio = total == 0 ? 0.0 : (double)totalOther/total * 100;
                double jobRatio = total == 0 ? 0.0 : (double)totalJob/total * 100;

                int ratio_i = (int) interRatio;
                int ratio_r = (int) resumeRatio;
                int ratio_o = (int) otherRatio;
                int ratio_j = (int) jobRatio;

                TextView myTextView1= (TextView) rootView1.findViewById(R.id.interview_percent);
                myTextView1.setText(Integer.toString(ratio_i) + "%");
                TextView myTextView= (TextView) rootView1.findViewById(R.id.resume_percent);
                myTextView.setText(Integer.toString(ratio_r) + "%");
                TextView myTextView2= (TextView) rootView1.findViewById(R.id.xxx_percent);
                myTextView2.setText(Integer.toString(ratio_j) + "%");
                TextView myTextView3= (TextView) rootView1.findViewById(R.id.others_percent);
                myTextView3.setText(Integer.toString(ratio_o) + "%");

                listAdapter_resume.notifyDataSetChanged();
                listAdapter_others.notifyDataSetChanged();
                listAdapter_xxx.notifyDataSetChanged();
                listAdapter_interview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

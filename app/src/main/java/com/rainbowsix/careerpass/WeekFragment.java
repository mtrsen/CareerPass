package com.rainbowsix.careerpass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.List;

/**
 * Created by lihan on 10/13/2017.
 */

public class WeekFragment extends Fragment {
    View rootView1;
    GridView list_interview, list_resume, list_xxx, list_others;
    ImageView right, left;
    TextView dayStart, dayEnd, MonthEnd, MonthStart, year;
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
    int curDate = Integer.parseInt(s[2]);
    int curYear = Integer.parseInt(s[5]);

    //current date to add to to do list
    Date nowTime = Calendar.getInstance().getTime();
    String[] now = nowTime.toString().split(" ");
    String nowMon = now[1];
    int nowDate = Integer.parseInt(now[2]);
    int nowYear = Integer.parseInt(now[5]);

    Button addtolist, addpost;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mFirebaseDatabaseReference;

    String name ;

    public WeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView1 = inflater.inflate(R.layout.week, container, false);
        initialize();


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
        dayStart = (TextView)rootView1.findViewById(R.id.day_start);
        MonthStart = (TextView)rootView1.findViewById(R.id.month_start);
        dayEnd = (TextView)rootView1.findViewById(R.id.day_end);
        MonthEnd = (TextView) rootView1.findViewById(R.id.month_end);
        year = (TextView)rootView1.findViewById(R.id.year);

        data_xxx = new ArrayList<TagSingle>();
        data_resume = new ArrayList<TagSingle>();
        data_interview = new ArrayList<TagSingle>();
        data_others = new ArrayList<TagSingle>();

        //===Read from database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference();

        dayEnd.setText(String.valueOf(curDate));
        MonthEnd.setText(curMon);
        year.setText(String.valueOf(curYear));

        setPreWeek(curDate, month, curMon, daysOfMonth, curYear);

        int index = Arrays.asList(month).indexOf(nowMon) + 1;
        String monthCur = index < 10 ? "0" + Integer.toString(index) : Integer.toString(index);
        final String date = Integer.toString(nowYear) + monthCur + (nowDate < 10 ? "0" + nowDate : nowDate);
        List<String> dateList = getDateList(nowYear,nowMon, nowDate);
        setContent(dateList);

        SharedPreferences sharedPre = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String email = sharedPre.getString("username","");

        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Added Successfully!", Toast.LENGTH_LONG).show();
                mFirebaseDatabaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = "aaa";
                        for(DataSnapshot singleUser : dataSnapshot.getChildren()){
                            String getEmail = singleUser.child("email").getValue().toString();
                            if (getEmail.equals(email)){
                                name = singleUser.getKey().toString();
                            }
                        }

                        for(int i = 0; i < data_interview.size(); i++){
                            if(Boolean.valueOf(data_interview.get(i).getAdded()) == true){
                                if(dataSnapshot.child(name).hasChild("todo") && dataSnapshot.child(name).child("todo").hasChild(data_interview.get(i).getTag())){
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_interview.get(i).getTag()).child("time").setValue(date);
                                }
                                else{
                                    toDoListBlock todo = new toDoListBlock("interview",date,data_interview.get(i).getTag(),"false");
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_interview.get(i).getTag()).setValue(todo);
                                }
                            }
                        }
                        for(int i = 0; i < data_resume.size(); i++){
                            if(Boolean.valueOf(data_resume.get(i).getAdded()) == true){
                                if(dataSnapshot.child(name).hasChild("todo") && dataSnapshot.child(name).child("todo").hasChild(data_resume.get(i).getTag())){
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_resume.get(i).getTag()).child("time").setValue(date);
                                }
                                else{
                                    toDoListBlock todo = new toDoListBlock("resume",date,data_resume.get(i).getTag(),"false");
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_resume.get(i).getTag()).setValue(todo);
                                }
                            }
                        }
                        for(int i = 0; i < data_xxx.size(); i++){
                            if(Boolean.valueOf(data_xxx.get(i).getAdded()) == true){
                                if(dataSnapshot.child(name).hasChild("todo") && dataSnapshot.child(name).child("todo").hasChild(data_xxx.get(i).getTag())){
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_xxx.get(i).getTag()).child("time").setValue(date);
                                }
                                else{
                                    toDoListBlock todo = new toDoListBlock("job search",date,data_xxx.get(i).getTag(),"false");
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_xxx.get(i).getTag()).setValue(todo);
                                }
                            }
                        }
                        for(int i = 0; i < data_others.size(); i++){
                            if(Boolean.valueOf(data_others.get(i).getAdded()) == true){
                                if(dataSnapshot.child(name).hasChild("todo") && dataSnapshot.child(name).child("todo").hasChild(data_others.get(i).getTag())){
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_others.get(i).getTag()).child("time").setValue(date);
                                }
                                else{
                                    toDoListBlock todo = new toDoListBlock("others",date,data_others.get(i).getTag(),"false");
                                    mFirebaseDatabaseReference.child("User").child(name).child("todo").child(data_others.get(i).getTag()).setValue(todo);
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
                dayEnd.setText(String.valueOf(curDate));
                MonthEnd.setText(curMon);
                int tempMon = Arrays.asList(month).indexOf(curMon);

                List<String> dateList = getDateList(curYear,curMon, curDate);
                setContent(dateList);
                if (curDate <= 7) {
                    if (tempMon == 0) {
                        tempMon = 11;
                        curYear--;
                    }
                    else tempMon--;
                    curDate = curDate - 7 + daysOfMonth[tempMon];
                }
                else curDate -= 7;
                curMon = month[tempMon];
                dayStart.setText(String.valueOf(curDate));
                MonthStart.setText(curMon);
                year.setText(String.valueOf(curYear));



            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayStart.setText(String.valueOf(curDate));
                MonthStart.setText(curMon);
                int tempMon = Arrays.asList(month).indexOf(curMon);
                if (curDate + 7 > daysOfMonth[tempMon]) {
                    curDate = curDate + 7 - daysOfMonth[tempMon];
                    if (tempMon == 11) {
                        tempMon = 0;
                        curYear++;
                    }
                    else tempMon++;
                }
                else curDate += 7;
                curMon = month[tempMon];
                dayEnd.setText(String.valueOf(curDate));
                MonthEnd.setText(curMon);
                year.setText(String.valueOf(curYear));
                List<String> dateList = getDateList(curYear,curMon, curDate);
                setContent(dateList);


            }
        });
    }

    private void setPreWeek(int curDate, String[] month, String curMon, int[] daysOfMonth,
                            int curYear) {
        dayEnd.setText(String.valueOf(curDate));
        MonthEnd.setText(curMon);
        int tempMon = Arrays.asList(month).indexOf(curMon);
        if (curDate <= 7) {
            if (tempMon == 0) {
                tempMon = 11;
                curYear--;
            }
            else tempMon--;
            curDate = curDate - 7 + daysOfMonth[tempMon];
        }
        else curDate -= 7;
        curMon = month[tempMon];
        dayStart.setText(String.valueOf(curDate));
        MonthStart.setText(curMon);
        year.setText(String.valueOf(curYear));
        System.out.println(curDate);
        System.out.println(curMon);
    }

    private void setNextWeek(int curDate, String[] month, String curMon, int[] daysOfMonth,
                             int curYear) {
        dayStart.setText(String.valueOf(curDate));
        MonthStart.setText(curMon);
        int tempMon = Arrays.asList(month).indexOf(curMon);
        if (curDate + 7 > daysOfMonth[tempMon]) {
            curDate = curDate + 7 - daysOfMonth[tempMon];
            if (tempMon == 11) {
                tempMon = 0;
                curYear++;
            }
            else tempMon++;
        }
        else curDate += 7;
        curMon = month[tempMon];
        dayEnd.setText(String.valueOf(curDate));
        MonthEnd.setText(curMon);
        year.setText(String.valueOf(curYear));
    }

    public List<String> getDateList(int yearTo, String monthTo, int dayTo){
        List<String> dateList = new ArrayList<String>();
        int count = 0;
        int monNow = Arrays.asList(month).indexOf(monthTo)+1;
        for(int i = dayTo; i > 0 && count < 7; i--){
            String str = dateStr(yearTo, monNow, i);
            dateList.add(str);
            count++;
        }
        if(count == 7){
            return dateList;
        }
        int monPre = monNow == 1 ? 12 : monNow - 1;
        int yearPre = monPre == 12 ? yearTo - 1 : yearTo;
        int lastDay = daysOfMonth[monPre-1];

        for(int i = lastDay; i > 0 && count < 7; i--){
            String str =dateStr(yearPre, monPre, i);
            dateList.add(str);
            count++;
        }
        return dateList;
    }
    public String dateStr(int year, int month, int day){
        String strMon = month < 10 ? "0" + Integer.toString(month) : Integer.toString(month);
        String strDay = day  < 10 ? "0" + Integer.toString(day) : Integer.toString(day);
        String dateStr = Integer.toString(year) + strMon + strDay;
        return dateStr;
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


                ((TextView) rootView1.findViewById(R.id.interview_percent)).setText(Integer.toString(ratio_i) + "%");
                ((TextView) rootView1.findViewById(R.id.resume_percent)).setText(Integer.toString(ratio_r) + "%");
                ((TextView) rootView1.findViewById(R.id.xxx_percent)).setText(Integer.toString(ratio_j) + "%");
                ((TextView) rootView1.findViewById(R.id.others_percent)).setText(Integer.toString(ratio_o) + "%");


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

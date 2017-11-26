package com.rainbowsix.careerpass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.value;

/**
 * Created by lihan on 10/13/2017.
 */

public class DayFragment extends Fragment {
    View rootView1;
    GridView list_interview, list_resume, list_xxx, list_others;
    ImageView right, left;
    TextView day, Month, year;
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
    ScrollView scrollView;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mFirebaseDatabaseReference;

    String name ;

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView1 = inflater.inflate(R.layout.day, container, false);
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
        day = (TextView)rootView1.findViewById(R.id.day);
        Month = (TextView)rootView1.findViewById(R.id.month);
        year = (TextView)rootView1.findViewById(R.id.year);

        data_xxx = new ArrayList<TagSingle>();
        data_resume = new ArrayList<TagSingle>();
        data_interview = new ArrayList<TagSingle>();
        data_others = new ArrayList<TagSingle>();

        day.setText(String.valueOf(curDate));
        Month.setText(curMon);
        year.setText(String.valueOf(curYear));

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        int index = Arrays.asList(month).indexOf(nowMon) + 1;
        String monthCur = index < 10 ? "0" + Integer.toString(index) : Integer.toString(index);
        final String date = Integer.toString(nowYear) + monthCur + (nowDate < 10 ? "0" + nowDate : nowDate);
        setContent(date);

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
                int tempMon = Arrays.asList(month).indexOf(curMon);
                if (curDate == 1) {
                    if (tempMon == 0) {
                        tempMon = 11;
                        curYear--;
                    }
                    else tempMon--;
                    curDate = daysOfMonth[tempMon];
                }
                else curDate--;
                curMon = month[tempMon];
                day.setText(String.valueOf(curDate));
                Month.setText(curMon);
                year.setText(String.valueOf(curYear));
                int monIndex = Arrays.asList(month).indexOf(curMon) + 1;
                String dataCur = curDate < 10 ? "0" + Integer.toString(curDate) : Integer.toString(curDate);
                String monthCur = monIndex < 10 ? "0" + Integer.toString(monIndex) : Integer.toString(monIndex);
                String date = Integer.toString(curYear) + monthCur + dataCur;
                setContent(date);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempMon = Arrays.asList(month).indexOf(curMon);
                if (curDate == daysOfMonth[tempMon]) {
                    if (tempMon == 11) {
                        tempMon = 0;
                        curYear++;
                    }
                    else tempMon++;
                    curDate = 1;
                }
                else curDate++;
                curMon = month[tempMon];
                day.setText(String.valueOf(curDate));
                Month.setText(curMon);
                year.setText(String.valueOf(curYear));
                int monIndex = Arrays.asList(month).indexOf(curMon) + 1;
                String dataCur = curDate < 10 ? "0" + Integer.toString(curDate) : Integer.toString(curDate);
                String monthCur = monIndex < 10 ? "0" + Integer.toString(monIndex) : Integer.toString(monIndex);
                String date = Integer.toString(curYear) + monthCur + dataCur;
                setContent(date);
            }
        });
    }

    public void setContent(String Date){
        data_resume.clear();
        data_interview.clear();
        data_others.clear();
        data_xxx.clear();
        TextView myTextView= (TextView) rootView1.findViewById(R.id.resume_percent);
        myTextView.setText("0%");
        TextView myTextView1= (TextView) rootView1.findViewById(R.id.interview_percent);
        myTextView1.setText("0%");
        TextView myTextView2= (TextView) rootView1.findViewById(R.id.xxx_percent);
        myTextView2.setText("0%");
        TextView myTextView3= (TextView) rootView1.findViewById(R.id.others_percent);
        myTextView3.setText("0%");

        final String date = Date;
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("post").hasChild(date)){

                    if(dataSnapshot.child("post").child(date).hasChild("resume")){
                        DataSnapshot snap = dataSnapshot.child("post").child(date).child("resume").child("tag");
                        DataSnapshot ratio_resume = dataSnapshot.child("post").child(date).child("resume").child("ratio");
                        TextView myTextView= (TextView) rootView1.findViewById(R.id.resume_percent);
                        myTextView.setText(ratio_resume.getValue().toString() + "%");

                        for(DataSnapshot post : snap.getChildren()){
                            PostCategory postCategory = post.getValue(PostCategory.class);
                            data_resume.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                        }
                    }
                    if(dataSnapshot.child("post").child(date).hasChild("interview")){

                        DataSnapshot snap1 = dataSnapshot.child("post").child(date).child("interview").child("tag");
                        DataSnapshot ratio_interview = dataSnapshot.child("post").child(date).child("interview").child("ratio");
                        TextView myTextView1= (TextView) rootView1.findViewById(R.id.interview_percent);
                        myTextView1.setText(ratio_interview.getValue().toString() + "%");
                        for(DataSnapshot post : snap1.getChildren()){
                            PostCategory postCategory = post.getValue(PostCategory.class);
                            data_interview.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                        }
                    }
                    if(dataSnapshot.child("post").child(date).hasChild("job search")){
                        DataSnapshot snap2 = dataSnapshot.child("post").child(date).child("job search").child("tag");
                        DataSnapshot ratio_xxx = dataSnapshot.child("post").child(date).child("job search").child("ratio");
                        TextView myTextView2= (TextView) rootView1.findViewById(R.id.xxx_percent);
                        myTextView2.setText(ratio_xxx.getValue().toString() + "%");
                        for(DataSnapshot post : snap2.getChildren()){
                            PostCategory postCategory = post.getValue(PostCategory.class);
                            data_xxx.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                        }
                    }
                    if(dataSnapshot.child("post").child(date).hasChild("others")){
                        DataSnapshot snap3 = dataSnapshot.child("post").child(date).child("others").child("tag");
                        DataSnapshot ratio_other = dataSnapshot.child("post").child(date).child("others").child("ratio");
                        TextView myTextView3= (TextView) rootView1.findViewById(R.id.others_percent);
                        myTextView3.setText(ratio_other.getValue().toString() + "%");
                        for(DataSnapshot post : snap3.getChildren()){
                            PostCategory postCategory = post.getValue(PostCategory.class);
                            data_others.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                        }
                    }
                }
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

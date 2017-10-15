package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;




import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.*;

import org.w3c.dom.Text;

/**
 * Created by lihan on 10/13/2017.
 */

public class DayFragment extends Fragment {
    View rootView1;
    ListView list_interview, list_resume, list_xxx, list_others;
    TagAdapter listAdapter_interview, listAdapter_resume, listAdapter_xxx, listAdapter_others;
    List<TagSingle> data_interview;
    List<TagSingle> data_resume;
    List<TagSingle> data_xxx;
    List<TagSingle> data_others;

    Button addtolist;
    ScrollView scrollView;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mFirebaseDatabaseReference;

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

        scrollView = (ScrollView)rootView1.findViewById(R.id.scrollview);
        list_interview = (ListView)rootView1.findViewById(R.id.interviewlist);
        list_resume = (ListView)rootView1.findViewById(R.id.resumelist);
        list_xxx = (ListView)rootView1.findViewById(R.id.xxxlist);
        list_others = (ListView)rootView1.findViewById(R.id.otherslist);
        addtolist = (Button)rootView1.findViewById(R.id.addtolist);


//        TextView myTextView= (TextView) rootView1.findViewById(R.id.interview_percent);
//        myTextView.setText(ratio_interview);

        data_xxx = new ArrayList<TagSingle>();
        data_resume = new ArrayList<TagSingle>();
        data_interview = new ArrayList<TagSingle>();
        data_others = new ArrayList<TagSingle>();

        //===Read from database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference();

        final String date = "20171001";
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot snap = dataSnapshot.child("post").child(date).child("resume").child("tag");

                DataSnapshot ratio_resume = dataSnapshot.child("post").child(date).child("resume").child("ratio");
                TextView myTextView= (TextView) rootView1.findViewById(R.id.resume_percent);
                myTextView.setText(ratio_resume.getValue().toString() + "%");

                for(DataSnapshot post : snap.getChildren()){
                    PostCategory postCategory = post.getValue(PostCategory.class);
                    data_resume.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                    listAdapter_resume.notifyDataSetChanged();
                }

                DataSnapshot snap1 = dataSnapshot.child("post").child(date).child("interview").child("tag");
                DataSnapshot ratio_interview = dataSnapshot.child("post").child(date).child("interview").child("ratio");
                TextView myTextView1= (TextView) rootView1.findViewById(R.id.interview_percent);
                myTextView1.setText(ratio_interview.getValue().toString() + "%");
                for(DataSnapshot post : snap1.getChildren()){
                    PostCategory postCategory = post.getValue(PostCategory.class);
                    data_interview.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                    listAdapter_interview.notifyDataSetChanged();
                }

                DataSnapshot snap2 = dataSnapshot.child("post").child(date).child("resume").child("tag");
                DataSnapshot ratio_xxx = dataSnapshot.child("post").child(date).child("resume").child("ratio");
                TextView myTextView2= (TextView) rootView1.findViewById(R.id.xxx_percent);
                myTextView2.setText(ratio_xxx.getValue().toString() + "%");
                for(DataSnapshot post : snap2.getChildren()){
                    PostCategory postCategory = post.getValue(PostCategory.class);
                    data_xxx.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                    listAdapter_xxx.notifyDataSetChanged();
                }

                DataSnapshot snap3 = dataSnapshot.child("post").child(date).child("resume").child("tag");
                DataSnapshot ratio_other = dataSnapshot.child("post").child(date).child("resume").child("ratio");
                TextView myTextView3= (TextView) rootView1.findViewById(R.id.others_percent);
                myTextView3.setText(ratio_other.getValue().toString() + "%");
                for(DataSnapshot post : snap3.getChildren()){
                    PostCategory postCategory = post.getValue(PostCategory.class);
                    data_others.add(new TagSingle(postCategory.getTag(), postCategory.getCount(), Boolean.valueOf(postCategory.getAdd())));
                    listAdapter_others.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                Toast.makeText(getActivity(), "Added Successfully!", Toast.LENGTH_LONG).show();
=======
                Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
                for(int i = 0; i < data_interview.size(); i++){
                    if(Boolean.valueOf(data_interview.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("interview",date,data_interview.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child("aaa").child(data_interview.get(i).getTag()).setValue(todo);
                    }
                }
                for(int i = 0; i < data_resume.size(); i++){
                    if(Boolean.valueOf(data_resume.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("resume",date,data_resume.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child("aaa").child(data_resume.get(i).getTag()).setValue(todo);

                    }
                }
                for(int i = 0; i < data_xxx.size(); i++){
                    if(Boolean.valueOf(data_xxx.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("xxx",date,data_xxx.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child("aaa").child(data_xxx.get(i).getTag()).setValue(todo);
                    }
                }
                for(int i = 0; i < data_others.size(); i++){
                    if(Boolean.valueOf(data_others.get(i).getAdded()) == true){
                        toDoListBlock todo = new toDoListBlock("others",date,data_others.get(i).getTag(),"false");
                        mFirebaseDatabase.getReference().child("aaa").child(data_others.get(i).getTag()).setValue(todo);
                    }
                }


>>>>>>> 7e721678b8b2dbb51119c6b8dd4599909e65300a
            }
        });
    }


}

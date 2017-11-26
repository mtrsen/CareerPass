package com.rainbowsix.careerpass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;

import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.R.attr.button;

public class PostActivity extends AppCompatActivity {

    EditText tag, date ;
    Spinner spinner_category, spinner_tag;
    Button cancel, post;
    String m_tag, m_cat;
    private DatabaseReference mDatabaseReference;

    String[] interviews = {"Prepare for Questions", "On Campus Interview", "Onsite Interview", "Phone interview", "Mock-up interview"};
    String[] resume = {"Write cover letter", "Revise Resume", "Go to workshops"};
    String[] job = {"LinkedIn", "Career fair", "Interview", "Asking professors", "Go to tech talks"};
    String[] others = {"BBS", "Project Overview", "Talking to C2D2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.85), (int)(height * 0.85));
        initialize();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void initialize() {
        //tag = (EditText)findViewById(R.id.tag_title);
        date = (EditText)findViewById(R.id.date);
        date.addTextChangedListener(new TextWatcher() {
            int prevL = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = date.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (prevL < editable.length() && (editable.length() == 2 || editable.length() == 5))
                    editable.append('/');
            }
        });
        spinner_category = (Spinner)findViewById(R.id.spinner_category);
        spinner_tag = (Spinner)findViewById(R.id.spinner_tag);
        cancel = (Button)findViewById(R.id.cancel);
        post = (Button)findViewById(R.id.post);
        String[] items = new String[]{"interview", "resume", "job search", "others"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, items);

        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_cat = adapterView.getItemAtPosition(i).toString();

                ArrayAdapter<String> ainterview = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, interviews);
                ArrayAdapter<String> aresume = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, resume);
                ArrayAdapter<String> ajob = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, job);
                ArrayAdapter<String> aothers = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, others);

                if (m_cat.equals("interview")) {
                    spinner_tag.setAdapter(ainterview);
                }
                else if (m_cat.equals("resume")) {
                    spinner_tag.setAdapter(aresume);
                }
                else if (m_cat.equals("job search")) {
                    spinner_tag.setAdapter(ajob);
                }
                else {
                    spinner_tag.setAdapter(aothers);
                }

                spinner_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        m_tag = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(m_tag)) {
                    Toast.makeText(getApplicationContext(), "Tag cannot be empty!", Toast.LENGTH_LONG).show();
                    return ;
                }
                if (TextUtils.isEmpty(date.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Date cannot be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                final String cate =  m_cat;
                //final String Date = date.getText().toString();

                StringBuilder sb = new StringBuilder();
                sb.append(date.getText().toString().substring(6) + date.getText().toString().substring(0, 2)
                        + date.getText().toString().substring(3, 5));
                final String Date = sb.toString();


                //add post to database and change the count of the post
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.child("post");
                        if(snap.hasChild(Date) && snap.child(Date).hasChild(cate) && snap.child(Date).child(cate).child("tag").hasChild(m_tag)){
                            int num = snap.child(Date).child(m_cat).child("count").getValue(Integer.class);
                            PostCategory userPost = new PostCategory("false",num+1, m_tag);
                            mDatabaseReference.child("post").child(Date).child(cate).child("tag").child(m_tag).setValue(userPost);

                        }
                        else{
                            PostCategory userPost = new PostCategory("false",1, m_tag);
                            mDatabaseReference.child("post").child(Date).child(cate).child("tag").child(m_tag).setValue(userPost);

                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //update count
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.child("post").child(Date).child(cate);
                        if (snap.hasChild("count")) {
                            int num = snap.child("count").getValue(Integer.class);
                            mDatabaseReference.child("post").child(Date).child(cate).child("count").setValue(num+1);
                        }
                        else{
                            mDatabaseReference.child("post").child(Date).child(cate).child("count").setValue(1);
                            mDatabaseReference.child("post").child(Date).child(cate).child("ratio").setValue(1);
                            mDatabaseReference.child("post").child(Date).child(cate).child("name").setValue(cate);
                        }


                        DataSnapshot snapShot = dataSnapshot.child("post").child(Date);
                        int totalInterview = 0, totalJob = 0, totalResume = 0, totalOther = 0 ;
                        if(cate.equals("interview"))    totalInterview++;
                        if(cate.equals("job search"))    totalJob++;
                        if(cate.equals("resume"))    totalResume++;
                        if(cate.equals("others"))   totalOther++;

                        if(snapShot.hasChild("interview")){
                            for(DataSnapshot oneTag : snapShot.child("interview").child("tag").getChildren()){
                                totalInterview += oneTag.child("count").getValue(Integer.class);
                            }
                        }
                        if(snapShot.hasChild("job search")){
                            for(DataSnapshot oneTag : snapShot.child("job search").child("tag").getChildren()){
                                totalJob += oneTag.child("count").getValue(Integer.class);
                            }
                        }
                        if(snapShot.hasChild("resume")){
                            for(DataSnapshot oneTag : snapShot.child("resume").child("tag").getChildren()){
                                totalResume += oneTag.child("count").getValue(Integer.class);
                            }
                        }
                        if(snapShot.hasChild("others")){
                            for(DataSnapshot oneTag : snapShot.child("others").child("tag").getChildren()){
                                totalOther += oneTag.child("count").getValue(Integer.class);
                            }
                        }

                        int total = totalInterview + totalJob + totalResume + totalOther;
                        if(totalInterview != 0){
                            double Ratio = (double)totalInterview/total * 100;
                            int ratio = (int) Ratio;
                            mDatabaseReference.child("post").child(Date).child("interview").child("ratio").setValue(ratio);
                        }
                        if(totalResume != 0){
                            double Ratio = (double)totalResume/total * 100;
                            int ratio = (int) Ratio;
                            mDatabaseReference.child("post").child(Date).child("resume").child("ratio").setValue(ratio);
                        }
                        if(totalOther != 0){
                            double Ratio = (double)totalOther/total * 100;
                            int ratio = (int) Ratio;
                            mDatabaseReference.child("post").child(Date).child("others").child("ratio").setValue(ratio);
                        }
                        if(totalJob != 0){
                            double Ratio = (double)totalJob/total * 100;
                            int ratio = (int) Ratio;
                            mDatabaseReference.child("post").child(Date).child("job search").child("ratio").setValue(ratio);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                open(post);
            }
        });
    }


    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("This post is successfully created!");
        alertDialogBuilder.setPositiveButton("Got it!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        Intent intent = new Intent(PostActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

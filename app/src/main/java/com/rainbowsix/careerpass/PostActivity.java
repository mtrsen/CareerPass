package com.rainbowsix.careerpass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    EditText tag, date, tip;
    Spinner spinner_category;
    Button cancel, post;
    String m_tag, m_cat, m_date, m_tip;

    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initialize();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void initialize() {
        tag = (EditText)findViewById(R.id.tag_title);
        date = (EditText)findViewById(R.id.date);
        tip = (EditText)findViewById(R.id.tip);
        spinner_category = (Spinner)findViewById(R.id.spinner_category);
        cancel = (Button)findViewById(R.id.cancel);
        post = (Button)findViewById(R.id.post);
        String[] items = new String[]{"interview", "resume", "job search", "others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, items);
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_cat = adapterView.getItemAtPosition(i).toString();
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
                if (TextUtils.isEmpty(tag.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Tag cannot be empty!", Toast.LENGTH_LONG).show();
                    return ;
                }
                if (TextUtils.isEmpty(date.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Date cannot be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                final String cate =  m_cat;

                PostCategory userPost = new PostCategory("false",1, tag.getText().toString());
                mDatabaseReference.child("post").child(date.getText().toString()).child(cate).child("tag").child(tag.getText().toString()).setValue(userPost);

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.child("post").child(date.getText().toString()).child(cate);
                        if (snap.hasChild("count")) {
                            int num = snap.child("count").getValue(Integer.class);
                            mDatabaseReference.child("post").child(date.getText().toString()).child(cate).child("count").setValue(num+1);
                        }
                        else{
                            mDatabaseReference.child("post").child(date.getText().toString()).child(cate).child("count").setValue(1);
                            mDatabaseReference.child("post").child(date.getText().toString()).child(cate).child("ratio").setValue(1);
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
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

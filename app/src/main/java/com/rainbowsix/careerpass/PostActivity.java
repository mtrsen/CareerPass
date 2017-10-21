package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PostActivity extends AppCompatActivity {

    EditText tag, date, tip;
    Spinner spinner_category;
    Button cancel, post;
    String m_tag, m_cat, m_date, m_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initialize();
    }

    public void initialize() {
        tag = (EditText)findViewById(R.id.tag_title);
        date = (EditText)findViewById(R.id.date);
        tip = (EditText)findViewById(R.id.tip);
        spinner_category = (Spinner)findViewById(R.id.spinner_category);
        cancel = (Button)findViewById(R.id.cancel);
        post = (Button)findViewById(R.id.post);
        String[] items = new String[]{"Interview", "Resume", "Job search", "Others"};
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
    }


}

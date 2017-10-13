package com.rainbowsix.careerpass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User guoyuan = new User("Guoyuan", "guoyuanwu@gatech.edu", "123", 2009);
        Map<String, String> map = new HashMap<>();
        map.put("Guoyuan", "hahaha");
        mDatabase.child("users").child(guoyuan.getUserName()).setValue(guoyuan);
    }
}

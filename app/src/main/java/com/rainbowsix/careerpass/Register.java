package com.rainbowsix.careerpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hansenzhao on 10/14/17.
 */

public class Register extends MainActivity implements View.OnClickListener {

    Button button;
    EditText username, password, email, enrollment;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        enrollment = (EditText) findViewById(R.id.grade);
        button = (Button) findViewById(R.id.register);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                writeNewUser(username.getText().toString(), email.getText().toString(),
                        password.getText().toString(), enrollment.getText().toString());
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }
    private void writeNewUser(String username, String email, String password, String enrollment) {
        User newUser = new User(username, email, password, enrollment);
        databaseReference.child("User").child(username).setValue(newUser);
    }
}

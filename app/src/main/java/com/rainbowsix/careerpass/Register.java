package com.rainbowsix.careerpass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hansenzhao on 10/14/17.
 */

public class Register extends MenuActivity implements View.OnClickListener {

    Button button;
    EditText username, password, email, enrollment;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        enrollment = (EditText) findViewById(R.id.grade);
        button = (Button) findViewById(R.id.register);
        progressBar = (ProgressBar) findViewById(R.id.progressRegister);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

//                writeNewUser(username.getText().toString(), email.getText().toString(),
//                        password.getText().toString(), enrollment.getText().toString());
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        String eMail = email.getText().toString().trim();
        String enrollMent = enrollment.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Enter User Name!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(eMail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(enrollMent)) {
            Toast.makeText(getApplicationContext(), "Enter enrollment year!", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(eMail, passWord).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, task.getException().getMessage() ,
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Register.this, "Register Successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }
                });
    }
//    private void writeNewUser(String username, String email, String password, String enrollment) {
//        User newUser = new User(username, email, password, enrollment);
//        databaseReference.child("User").child(username).setValue(newUser);
//        firebaseAuth.createUserWithEmailAndPassword(username, email);
//    }
}

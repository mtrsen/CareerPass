package com.rainbowsix.careerpass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hansenzhao on 10/14/17.
 */

public class Login extends MenuActivity implements View.OnClickListener {


    Button button;
    EditText email, password;
    TextView registerLink;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.Login);
        registerLink = (TextView) findViewById(R.id.registerLink);
        progressBar = (ProgressBar) findViewById(R.id.progressLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login:
                final String Email = email.getText().toString();
                final String passWord = password.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Enter your username!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(passWord)) {
                    Toast.makeText(getApplicationContext(), "Enter your password!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(Email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPre = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPre.edit();
                            editor.putString("username",Email);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                break;
            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}

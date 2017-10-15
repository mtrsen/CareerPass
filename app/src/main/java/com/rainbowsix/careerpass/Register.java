package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hansenzhao on 10/14/17.
 */

public class Register extends MainActivity implements View.OnClickListener {

    Button button;
    EditText username, password, email, enrollment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        enrollment = (EditText) findViewById(R.id.grade);
        button = (Button) findViewById(R.id.register);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                break;
        }

    }
}

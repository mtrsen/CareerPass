package com.rainbowsix.careerpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hansenzhao on 10/14/17.
 */

public class Login extends MainActivity implements View.OnClickListener {


    Button button;
    EditText username, password;
    TextView registerLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.Login);
        registerLink = (TextView) findViewById(R.id.registerLink);

        button.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}

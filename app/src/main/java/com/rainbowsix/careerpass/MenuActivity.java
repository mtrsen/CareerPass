package com.rainbowsix.careerpass;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuActivity extends AppCompatActivity implements NavigationView.
        OnNavigationItemSelectedListener {
    //private DatabaseReference database;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    TextView nameTextView;
    TextView emailTextView;
    DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        nameTextView = navigationView.findViewById(R.id.nameView);
        emailTextView = navigationView.findViewById(R.id.emailView);

        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String email = sharedPre.getString("username","");
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.child("User");
                for(DataSnapshot singleUser : snap.getChildren()){
                    String getEmail = singleUser.child("email").getValue().toString();
                    if (getEmail.equals(email)){
                     //   nameTextView.setText(singleUser.getKey().toString());

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //emailTextView.setText(email);


        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity(new Intent(this, Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()){
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else if (id == R.id.nav_search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        else {
            startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

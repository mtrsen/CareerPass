package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import android.view.View;


public class HomeActivity extends MenuActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fragment,frameLayout);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        ViewAdapter va = new ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(va);
        TabLayout tbl = (TabLayout)findViewById(R.id.tablayout);
        tbl.setupWithViewPager(viewPager);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name1 = bundle.getString("email");
            String name2 = bundle.getString("name");
            Log.v("email", name1);
            Log.v("name", name2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // to check current activity in the navigation drawer
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}

package com.rainbowsix.careerpass;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fragment, frameLayout);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        ViewAdapter va = new ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(va);
        TabLayout tbl = (TabLayout)findViewById(R.id.tablayout);
        tbl.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}

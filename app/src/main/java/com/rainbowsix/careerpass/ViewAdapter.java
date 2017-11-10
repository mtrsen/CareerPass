package com.rainbowsix.careerpass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lihan on 10/13/2017.
 */

public class ViewAdapter extends FragmentPagerAdapter {
    public static final String MONTHLY = "Monthly";
    public static final String WEEKLY = "Weekly";
    public static final String DAILY = "Daily";

    public Fragment[] pages = new Fragment[] {
            new MonthFragment(),
            new WeekFragment(),
            new DayFragment()
    };

    private String[] title = new String[] {MONTHLY, WEEKLY, DAILY};

    public ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}

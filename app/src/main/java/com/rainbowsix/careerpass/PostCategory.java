package com.rainbowsix.careerpass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leyna on 10/15/17.
 */

public class PostCategory {
    public String add;
    public int count;
    public String tag;

    public PostCategory() {
    }

    public PostCategory(String add, int count, String tag) {
        this.tag = tag;
        this.count = count;
        this.add = add;
    }

    public String getTag() {
        return tag;
    }

    public int getCount() {
        return count;
    }
    public String getAdd(){
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}



package com.rainbowsix.careerpass;

/**
 * Created by lihan on 10/14/2017.
 */

public class TagSingle {
    String tag;
    int count;
    boolean added;

    public TagSingle(String tag, int count, boolean added) {
        this.tag = tag;
        this.count = count;
        this.added = added;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getTag(){return this.tag;}

    public int getCount() {return this.count;}

    public boolean getAdded() {return this.added;}
}

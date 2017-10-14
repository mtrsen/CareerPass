package com.rainbowsix.careerpass;

/**
 * Created by lihan on 10/9/2017.
 */

public class ListSingle {
    String tag = "";
    String time = "";
    String cat = "";
    boolean checked = false;

    public ListSingle(String tag, String time, String cat, boolean checked) {
        this.tag = tag;
        this.time = time;
        this.cat = cat;
        this.checked = checked;
    }

    public String getTag() {return tag;}
    public String getTime() {return time;}
    public String getCat() {return cat;}
    public boolean getChecked() {return checked;}

    public void setTag(String tag) {this.tag = tag;}
    public void setTime(String time) {this.time = time;}
    public void setCat(String cat) {this.cat = cat;}
    public void setChecked(boolean checked) {this.checked = checked;}
}

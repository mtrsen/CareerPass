package com.rainbowsix.careerpass;

/**
 * Created by guoyuanwu on 10/14/17.
 */

public class toDoListBlock {
    public String category;
    public String time;
    public String name;

    public String getCategory() {
        return category;
    }
    public toDoListBlock() {}
    public void setCategory(String category) {
        this.category = category;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getTime() {

        return time;
    }

    public String complete;
    public toDoListBlock(String category, String time, String name, String complete) {
        this.category = category;
        this.time = time;
        this.name = name;
        this.complete = complete;
    }
}

package com.rainbowsix.careerpass;

/**
 * Created by guoyuanwu on 10/13/17.
 */

public class User {
    String userName;
    String email;
    String passWord;
    String grade;
    public User(String userName, String email, String passWord, String grade) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.grade = grade;
    }
    public User() {}
    public String getUserName() {
        return this.userName;
    }
}

package com.rainbowsix.careerpass;

/**
 * Created by guoyuanwu on 10/13/17.
 */

public class User {
    String userName;
    String email;
    String passWord;
    int grade;
    public User(String userName, String email, String passWord, int grade) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.grade = grade;
    }
    public String getUserName() {
        return this.userName;
    }
}

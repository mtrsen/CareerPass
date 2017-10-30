package com.rainbowsix.careerpass;

/**
 * Created by guoyuanwu on 10/13/17.
 */

public class User {
    String userName;
    String email;
    String passWord;
    String grade;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {

        return email;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getGrade() {
        return grade;
    }

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

package com.rrr.swift.SupportClasses;

public class User {

    public String mFirstName;
    public String mLastName;
    public String mEmail;
    public String mPassword;
    public Boolean mAdmin;

    public User(){

    }

    public User(String mFirstName, String mLastName, String mEmail, String mPassword, Boolean mAdmin){

        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mAdmin = mAdmin;
    }

}


package com.uta.eprescription.authenticationMgr;

public class User {

    String userName;
    String userEmailId;
    /*String userId;*/
    String userType;
    /*String userGender;
    String password;*/

    public User() {

    }

    public User(String userName, String userEmailId/*, String userId*/, String userType/*, String userGender, String password*/) {
        this.userName = userName;
        this.userEmailId = userEmailId;
        /*this.userId = userId;*/
        this.userType = userType;
        /*this.userGender = userGender;
        this.password = password;*/
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

   /* public String getUserId() {
        return userId;
    }*/

    public String getUserType() {
        return userType;
    }

  /*  public String getUserGender() {
        return userGender;
    }

    public String getPassword() {
        return password;
    }*/
}

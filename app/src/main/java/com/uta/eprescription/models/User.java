package com.uta.eprescription.models;

public class User {

    String userId;
    String password;
    String firstName;
    String lastName;
    String emailId;
    String userType;

    public User(String userId, String userPassword,String emailId, String firstName, String lastName,String userType) {
        this.userId = userId;
        this.password =userPassword;
        this.emailId = emailId;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean authorizedUser(String userId, String password) {
        if(this.userId == userId && this.password == password)
            return true;
        return false;
    }
}

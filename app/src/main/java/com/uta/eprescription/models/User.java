package com.uta.eprescription.models;

public class User {

    String userId;
    String password;
    String firstName;
    String lastName;
    String Phone;
    String DOB;
    String userType;

    public User(){

    }
    public User(String userId, String userPassword,String Phone, String firstName, String lastName,String userType,String DOB) {
        this.userId = userId;
        this.password = userPassword;
        this.Phone = Phone;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

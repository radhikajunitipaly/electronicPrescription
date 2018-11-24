package com.uta.eprescription.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private String Phone;
    private String DOB;
    private String userType;
    private ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

    public User(){

    }
    public User(String userId, String userPassword,String Phone, String firstName, String lastName,
                String userType,String DOB, ArrayList<Prescription> prescriptionList) {
        this.userId = userId;
        this.password = userPassword;
        this.Phone = Phone;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.prescriptions = prescriptionList;
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

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }


    public String getPhone() {
        return Phone;
    }

    public String getDOB() {
        return DOB;
    }



}

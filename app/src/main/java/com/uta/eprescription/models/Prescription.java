package com.uta.eprescription.models;

public class Prescription {

    private String medicine;
    private String power;
    private String startDate;
    private String endDate;
    private String count;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String pid;


    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Prescription(){

    }
    public Prescription(String medicine, String power,String startDate, String endDate,
                        String count, String pid) {
        this.medicine = medicine;
        this.power = power;
        this.startDate = startDate;
        this.endDate = endDate;
        this.count = count;
        this.pid = pid;
    }

}


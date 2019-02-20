package com.first.msql;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

import android.content.Intent;

public class testset {
    public int tid;
    public int sid;
    public String testset;
    public String date;
    public String date1;
    public String status;

    public testset(int tid, String test1, String s, String no) {
        this.tid=tid;
        this.testset=test1;
        this.date=s;
       this.status=no;
    }

    public testset() {

    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getSid() {
        return sid;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public int getTid() {
        return tid;
    }

    public String getDate1() {
        return date1;
    }

    public void setTestset(String testset) {
        this.testset = testset;
    }

    public String getTestset() {
        return testset;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

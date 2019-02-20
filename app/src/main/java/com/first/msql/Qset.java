package com.first.msql;

public class Qset {
    public String qid;
    int status ;
    public String questions;

    public Qset(String id, String s) {
        this.qid=id;
        this.questions=s;
    }

    public Qset() {

    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQid() {
        return qid;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public Object getQuestions() {
        return questions;
    }
}

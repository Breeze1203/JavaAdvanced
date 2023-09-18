package com.example.springbootmail.service;

import java.util.Date;

public class MailInfo {
    public String setTo;
    public String setFrom;

    public Date date;

    public String getSetTo() {
        return setTo;
    }

    public void setSetTo(String setTo) {
        this.setTo = setTo;
    }

    public String getSetFrom() {
        return setFrom;
    }

    public void setSetFrom(String setFrom) {
        this.setFrom = setFrom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

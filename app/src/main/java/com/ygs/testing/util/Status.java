package com.ygs.testing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class Status {
    private int id;
    private int status;
    private Date date;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Status(int id, int status, Date date) {
        this.id = id;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {


        String dateString = format.format( date);
        return String.format("%-6d %d %13s",id,status,dateString);
    }
}

package com.example.msvs5;

import android.app.Application;

public class student {
     String name;
     String id;
     String dept;
     String email;
     String barcodeid;
     String key;

    public student() {

    }

    public student(String name, String id, String dept, String email, String barcodeid,String key) {
        this.name = name;
        this.id = id;
        this.dept = dept;
        this.email = email;
        this.barcodeid = barcodeid;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBarcodeid() {
        return barcodeid;
    }

    public void setBarcodeid(String barcodeid) {
        this.barcodeid = barcodeid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

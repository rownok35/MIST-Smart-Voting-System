package com.example.msvs5;

public class Voter {
    String name,id,dept,email,barcodeid;

    public Voter() {
    }

    public Voter(String name, String id, String dept, String email, String barcodeid) {
        this.name = name;
        this.id = id;
        this.dept = dept;
        this.email = email;
        this.barcodeid = barcodeid;
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
}

package com.example.msvs5;

public class DataSetFire {
    String name;
    String id;
    String dept;
    String cg;
    String department;
    int vote;
    String email;
    String barcodeid,propaganda,imgurl,event,post,key,phone,level;


    public DataSetFire() {
    }

    public DataSetFire(String name, String id, String dept, String cg, String department, int vote, String email, String barcodeid,String propaganda,String imgurl,String event,String post,String key,String phone,String level) {
        this.name = name;
        this.id = id;
        this.dept = dept;
        this.cg = cg;
        this.department = department;
        this.vote = vote;
        this.email = email;
        this.barcodeid = barcodeid;
        this.propaganda=propaganda;
        this.imgurl=imgurl;
        this.event=event;
        this.post=post;
        this.key=key;
        this.phone=phone;
        this.level=level;

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

    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote; }

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


    public String getPropaganda() {
        return propaganda;
    }

    public void setPropaganda(String propaganda) {
        this.propaganda = propaganda;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

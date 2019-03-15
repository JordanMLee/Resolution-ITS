package com.resolutionITS.application.entities;

public class Users {
    private String name;
    private String username;
    private String password;
    private String usertype;

    public Users () {}

    public Users(String name,String username, String password, String usertype) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }
}
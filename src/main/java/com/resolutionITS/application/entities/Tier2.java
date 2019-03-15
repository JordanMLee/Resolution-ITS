package com.resolutionITS.application.entities;

public class Tier2 extends Users {

    private String category;
    //private String username;

    public Tier2(String category) {
        this.category = category;
        //this.username = username;
    }


    public Tier2() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

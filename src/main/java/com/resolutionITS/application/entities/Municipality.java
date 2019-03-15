package com.resolutionITS.application.entities;

public class Municipality extends Users{

    private String category;
    //private String username;

    public Municipality(String category) {
        this.category = category;
        //this.username = username;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Municipality() {}


}

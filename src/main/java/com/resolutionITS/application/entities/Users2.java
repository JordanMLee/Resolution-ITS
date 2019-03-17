package com.resolutionITS.application.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users2 {

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Users2() {
    }

    public Users2(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

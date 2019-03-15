package com.resolutionITS.application.entities;


public class Company extends Users {
    private String hq;
    private int employeecnt;


    public Company(String username, String hq, int employeecnt) {
        this.hq = hq;
        this.employeecnt = employeecnt;
    }

    public Company() {}

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public int getEmployeecnt() {
        return employeecnt;
    }

    public void setEmployeecnt(int employeecnt) {
        this.employeecnt = employeecnt;
    }
}

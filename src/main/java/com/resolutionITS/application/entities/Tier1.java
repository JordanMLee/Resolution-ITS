package com.resolutionITS.application.entities;


public class Tier1 extends Users {
    private String hq;
    private int employeecnt;


    public Tier1(String username, String hq, int employeecnt) {
        this.hq = hq;
        this.employeecnt = employeecnt;
    }

    public Tier1() {
    }

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

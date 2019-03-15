package com.resolutionITS.application.entities;

public class TechnicianReport {
    private int skillid;
    private String skilldesc;
    private int totaltechnicians;
    private int techniciansInUse;

    public TechnicianReport() {
    }

    public TechnicianReport(int skillid, String skilldesc, int totaltechnicians, int techniciansInUse) {
        this.skillid = skillid;
        this.skilldesc = skilldesc;
        this.totaltechnicians = totaltechnicians;
        this.techniciansInUse = techniciansInUse;
    }

    public TechnicianReport(int skillid, String skilldesc, int technicians) {
        this.skillid = skillid;
        this.skilldesc = skilldesc;
        this.totaltechnicians = technicians;

    }


    public int getskillid() {
        return skillid;
    }

    public int getTotaltechnicians() {
        return totaltechnicians;
    }

    public int gettechniciansInUse() {
        return techniciansInUse;
    }

    public String getskilldesc() {
        return skilldesc;
    }
}

package com.resolutionITS.application.entities;

import java.sql.Date;

public class TechnicianStatus {
    private int technicianid;
    private String technicianname;
    private String incidentDescription;
    private String owner;
    private Date returndate;
    private Date startdate;

    public TechnicianStatus() {
    }

    public TechnicianStatus(int technicianid, String technicianname, String incidentDescription,
                            String owner, Date returndate, Date startdate) {
        this.technicianid = technicianid;
        this.technicianname = technicianname;
        this.incidentDescription = incidentDescription;
        this.owner = owner;
        this.startdate = startdate;
        this.returndate = returndate;

    }

    public TechnicianStatus(int technicianid, String technicianname, String incidentDescription,
                            String owner, Date returndate) {
        this.technicianid = technicianid;
        this.technicianname = technicianname;
        this.incidentDescription = incidentDescription;
        this.owner = owner;

        this.returndate = returndate;

    }

    public int gettechnicianid() {
        return technicianid;
    }

    public void settechnicianid(int technicianid) {
        this.technicianid = technicianid;
    }

    public String gettechnicianname() {
        return technicianname;
    }

    public void settechnicianname(String technicianname) {
        this.technicianname = technicianname;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }
}


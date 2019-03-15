package com.resolutionITS.application.entities;

import java.sql.Date;

public class ResourceStatus {
    private int resourceid;
    private String resourcename;
    private String incidentDescription;
    private String owner;
    private Date returndate;
    private Date startdate;

    public ResourceStatus() {}

    public ResourceStatus(int resourceid, String resourcename, String incidentDescription,
                          String owner, Date returndate, Date startdate) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.incidentDescription = incidentDescription;
        this.owner = owner;
        this.startdate = startdate;
        this.returndate = returndate;

    }

    public ResourceStatus(int resourceid, String resourcename, String incidentDescription,
                          String owner, Date returndate) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.incidentDescription = incidentDescription;
        this.owner = owner;

        this.returndate = returndate;

    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
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


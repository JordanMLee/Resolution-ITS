package com.resolutionITS.application.entities;

import java.sql.Date;

public class Is_deployed_to {

    private int resourceid;
    private int incidentid;
    private Date returndate;
    private Date startdate;
    private Date deployeddate;

    public Is_deployed_to(int resourceid, int incidentid, Date returndate, Date startdate, Date deployeddate) {
        this.resourceid = resourceid;
        this.incidentid = incidentid;
        this.returndate = returndate;
        this.startdate = startdate;
        this.deployeddate = deployeddate;

    }

    public Is_deployed_to() {}

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public int getIncidentid() {
        return incidentid;
    }

    public void setIncidentid(int incidentid) {
        this.incidentid = incidentid;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getDeployeddate() {
        return deployeddate;
    }

    public void setDeployeddate(Date deployeddate) {
        this.deployeddate = deployeddate;
    }
}

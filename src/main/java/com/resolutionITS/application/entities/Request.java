package com.resolutionITS.application.entities;

import java.sql.Date;

public class Request {
    private String username;
    private int resourceid;
    private int incidentid;
    private Date returndate;

   public Request(){}

   public Request(String username, int resourceid, int incidentid, Date returndate) {
       this.username = username;
       this.resourceid = resourceid;
       this.incidentid = incidentid;
       this.returndate = returndate;
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    @Override
    public String toString(){
       return "Request From: " + username + " for resource: " + " to be used in incident " + incidentid;

    }
}

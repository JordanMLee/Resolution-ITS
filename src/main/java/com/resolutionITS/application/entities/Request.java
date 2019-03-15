package com.resolutionITS.application.entities;

import java.sql.Date;

public class Request {
    private String username;
    private int technicianid;
    private int issueid;
    private Date returndate;

    public Request() {
    }

    public Request(String username, int technicianid, int issueid, Date returndate) {
        this.username = username;
        this.technicianid = technicianid;
        this.issueid = issueid;
        this.returndate = returndate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int gettechnicianid() {
        return technicianid;
    }

    public void settechnicianid(int technicianid) {
        this.technicianid = technicianid;
    }

    public int getissueid() {
        return issueid;
    }

    public void setissueid(int issueid) {
        this.issueid = issueid;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    @Override
    public String toString() {
        return "Request From: " + username + " for technician: " + " to be used in issue " + issueid;

    }
}

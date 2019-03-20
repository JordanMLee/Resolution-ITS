package com.resolutionITS.application.entities;

import java.sql.Date;

public class Issue {

    String username;
    int uniqid;
    int issueid;
    Date issuedate;
    String description;


    public Issue() {
    }

    public Issue(String username, int uniqid, int issueid,
                 Date issuedate, String description) {
        this.username = username;
        this.uniqid = uniqid; //example 1234
        this.issueid = issueid; //example MD-[issueid]
        this.issuedate = issuedate;
        this.description = description;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUniqid() {
        return uniqid;
    }

    public void setUniqid(int uniqid) {
        this.uniqid = uniqid;
    }

    public int getissueid() {
        return issueid;
    }

    public void setissueid(int issueid) {
        this.issueid = issueid;
    }

    public Date getissuedate() {
        return issuedate;
    }

    public void setissuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "[ Issue: " + uniqid + " with issueid: " + issueid;
    }


}

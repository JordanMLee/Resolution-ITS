package com.resolutionITS.application.entities;

import java.sql.Date;

public class Incident {

    String username;
    int uniqid;
    int incidentid;
    Date incidentdate;
    String description;
    float latitude;
    float longitude;

    public Incident() {}

    public Incident(String username, int uniqid, int incidentid,
                    Date incidentdate, String description, float latitude, float longitude) {
        this.username = username;
        this.uniqid = uniqid; //example 1234
        this.incidentid = incidentid; //example MD-[incidentid]
        this.incidentdate = incidentdate;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getIncidentid() {
        return incidentid;
    }

    public void setIncidentid(int incidentid) {
        this.incidentid = incidentid;
    }

    public Date getIncidentdate() {
        return incidentdate;
    }

    public void setIncidentdate(Date incidentdate) {
        this.incidentdate = incidentdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return  "[ Incident: " + String.valueOf(uniqid) + " with incidentid: " + String.valueOf(incidentid);
    }




}

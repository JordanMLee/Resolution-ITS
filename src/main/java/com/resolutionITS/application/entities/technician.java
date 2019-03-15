package com.resolutionITS.application.entities;


import java.sql.Date;
import java.util.List;

public class technician {
    int technicianid;
    String username;
    String technicianname;
    short skillid;
    String model;
    float latitude;
    float longitude;
    short maxdist;

    List<String> capabilites;
    Time time;
    int value;
    String unit;
    Boolean in_use;
    Date returndate;

    Date startdate;
    String description;

    public technician() {
    }

    public technician(int technicianid) {
        this.technicianid = technicianid;
    }

    public technician(int technicianid, String username, String technicianname, short skillid, String model, float latitude, float longitude, short maxdist, List<String> capabilites) {
        this.technicianid = technicianid;
        this.username = username;
        this.technicianname = technicianname;
        this.skillid = skillid;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxdist = maxdist;
        this.capabilites = capabilites;
    }

    public technician(int technicianid, String username, String technicianname) {
        this.technicianid = technicianid;
        this.username = username;
        this.technicianname = technicianname;


    }

    // special constructor for search keyword
    //technicianid | technicianname | ownername |  time   | unit | in_use | returndate
    public technician(int technicianid, String technicianname, String username, int value, String unit, Boolean in_use, Date returndate) {
        this.technicianid = technicianid;
        this.technicianname = technicianname;
        this.username = username;
        this.value = value;
        this.unit = unit;
        this.in_use = in_use;
        this.returndate = returndate;

    }

    //special constructor for gettechnicianRequests
    public technician(int technicianid, String technicianname, String description, String username, Date startdate, Date returndate) {
        this.technicianid = technicianid;
        this.technicianname = technicianname;
        this.description = description;
        this.username = username;
        this.startdate = startdate;
        this.returndate = returndate;

    }

    //special constructor for getMyRequestedtechnicians and gettechniciansInUse
    public technician(int technicianid, String technicianname, String description, String username, Date returndate) {
        this.technicianid = technicianid;
        this.technicianname = technicianname;
        this.description = description;
        this.username = username;
        this.returndate = returndate;

    }

    public technician(int technicianid, String username, String technicianname, short skillid, String model, float latitude, float longitude, short maxdist, List<String> capabilites, Time time, int value, String unit, Boolean in_use, Date returndate, Date startdate, String description) {
        this.technicianid = technicianid;
        this.username = username;
        this.technicianname = technicianname;
        this.skillid = skillid;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxdist = maxdist;
        this.capabilites = capabilites;
        this.time = time;
        this.value = value;
        this.unit = unit;
        this.in_use = in_use;
        this.returndate = returndate;
        this.startdate = startdate;
        this.description = description;
    }

    public int gettechnicianid() {
        return technicianid;
    }

    public void settechnicianid(int technicianid) {
        this.technicianid = technicianid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String gettechnicianname() {
        return technicianname;
    }

    public void settechnicianname(String technicianname) {
        this.technicianname = technicianname;
    }

    public short getskillid() {
        return skillid;
    }

    public void setskillid(short skillid) {
        this.skillid = skillid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public short getMaxdist() {
        return maxdist;
    }

    public void setMaxdist(short maxdist) {
        this.maxdist = maxdist;
    }

    public List<String> getCapabilites() {
        return capabilites;
    }

    public void setCapabilites(List<String> capabilites) {
        this.capabilites = capabilites;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    //============================


    public Date getReturndate() {
        return returndate;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartdate() {
        return startdate;
    }

    @Override
    public String toString() {
        return "technician [technician id=" + technicianid + ", owner=" + username + ", technician name=" + technicianname +
                ", skill id=" + skillid + ", model=" + model + ", latitude=" + latitude + ", longitude=" + longitude +
                ", maxdist=" + maxdist + "]";
    }

}

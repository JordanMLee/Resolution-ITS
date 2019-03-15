package com.resolutionITS.application.entities;


import java.sql.Date;
import java.util.List;

public class Resource {
    int resourceid;
    String username;
    String resourcename;
    short esfid;
    String model;
    float latitude;
    float longitude;
    short maxdist;

    List<String> capabilites;
    Cost cost;
    int value;
    String unit;
    Boolean in_use;
    Date returndate;

    Date startdate;
    String description;

    public Resource() {}

    public Resource(int resourceid) {
        this.resourceid = resourceid;
    }

    public Resource(int resourceid, String username, String resourcename, short esfid, String model, float latitude, float longitude, short maxdist, List<String> capabilites) {
        this.resourceid = resourceid;
        this.username = username;
        this.resourcename = resourcename;
        this.esfid = esfid;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxdist = maxdist;
        this.capabilites = capabilites;
    }

    public Resource(int resourceid, String username, String resourcename) {
        this.resourceid = resourceid;
        this.username = username;
        this.resourcename = resourcename;


    }

    // special constructor for search keyword
    //resourceid | resourcename | ownername |  cost   | unit | in_use | returndate
    public Resource(int resourceid, String resourcename, String username , int value, String unit, Boolean in_use ,Date returndate) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.username = username;
        this.value = value;
        this.unit = unit;
        this.in_use = in_use;
        this.returndate = returndate;

    }

    //special constructor for getResourceRequests
    public Resource(int resourceid, String resourcename, String description ,String username,Date startdate, Date returndate) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.description = description;
        this.username = username;
        this.startdate = startdate;
        this.returndate = returndate;

    }

    //special constructor for getMyRequestedResources and getResourcesInUse
    public Resource(int resourceid, String resourcename, String description ,String username, Date returndate) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.description = description;
        this.username = username;
        this.returndate = returndate;

    }

    public Resource(int resourceid, String username, String resourcename, short esfid, String model, float latitude, float longitude, short maxdist, List<String> capabilites, Cost cost, int value, String unit, Boolean in_use, Date returndate, Date startdate, String description) {
        this.resourceid = resourceid;
        this.username = username;
        this.resourcename = resourcename;
        this.esfid = esfid;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxdist = maxdist;
        this.capabilites = capabilites;
        this.cost = cost;
        this.value = value;
        this.unit = unit;
        this.in_use = in_use;
        this.returndate = returndate;
        this.startdate = startdate;
        this.description = description;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public short getEsfid() {
        return esfid;
    }

    public void setEsfid(short esfid) {
        this.esfid = esfid;
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

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
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
        return "Resource [resource id=" + resourceid + ", owner=" + username + ", resource name=" + resourcename +
                ", esf id=" + esfid + ", model=" + model + ", latitude=" + latitude + ", longitude=" + longitude +
                ", maxdist=" + maxdist + "]";
    }

}

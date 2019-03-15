package com.resolutionITS.application.entities;


public class Skill {

    int skillid;
    String skilldesc;

    public Skill() {
    }

    public Skill(int skillid, String skilldesc) {
        this.skillid = skillid;
        this.skilldesc = skilldesc;
    }

    public int getskillid() {
        return skillid;
    }

    public void setskillid(int skillid) {
        this.skillid = skillid;
    }

    public String getskilldesc() {
        return skilldesc;
    }

    public void setskilldesc(String skilldesc) {
        this.skilldesc = skilldesc;
    }

    @Override
    public String toString() {
        return getskilldesc();
    }


}

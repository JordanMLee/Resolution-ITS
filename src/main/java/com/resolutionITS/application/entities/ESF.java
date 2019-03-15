package com.resolutionITS.application.entities;


public class ESF {

    int esfid;
    String esfdesc;

    public ESF() {}

    public ESF(int esfid, String esfdesc) {
        this.esfid = esfid;
        this.esfdesc = esfdesc;
    }

    public int getEsfid() {
        return esfid;
    }

    public void setEsfid(int esfid) {
        this.esfid = esfid;
    }

    public String getEsfdesc() {
        return esfdesc;
    }

    public void setEsfdesc(String esfdesc) {
        this.esfdesc = esfdesc;
    }

    @Override
    public String toString(){
        return getEsfdesc();
    }


}

package com.resolutionITS.application.entities;

public class Incident_declaration {

    String declaration;
    int uniqid;
    int incidentid;

    public Incident_declaration(){}

    public Incident_declaration(String declaration, int uniqid, int incidentid) {
        this.declaration = declaration;
        this.uniqid = uniqid;
        this.incidentid = incidentid;
    }

    public String getDeclaration() {
        return declaration;
    }

    public int getIncidentid() {
        return incidentid;
    }

    public int getUniqid() {
        return uniqid;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public void setIncidentid(int incidentid) {
        this.incidentid = incidentid;
    }

    public void setUniqid(int uniqid) {
        this.uniqid = uniqid;
    }
    @Override
    public String toString(){
        return declaration;
    }
}

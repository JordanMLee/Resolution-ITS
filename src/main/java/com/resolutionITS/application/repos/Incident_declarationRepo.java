package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Incident_declaration;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringComponent
public class Incident_declarationRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public String selectuniqID(String declaration)  {
        String sql = "SELECT uniqid FROM incident_declaration WHERE incidentid = (SELECT MAX (incidentid) FROM incident_declaration WHERE declaration = ?::declarations)";
        return jdbcTemplate.queryForObject(sql,new Object[] {declaration},String.class);

    }

    public void insert (Incident_declaration incident_declaration) {
        jdbcTemplate.update("INSERT INTO incident_declaration (declaration, uniqid, incidentid) VALUES (?::declarations,?,?)",
                new Object[] { incident_declaration.getDeclaration(), incident_declaration.getUniqid(),
                        incident_declaration.getIncidentid()});
    }
}



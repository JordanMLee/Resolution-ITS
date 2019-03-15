package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Incident;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
public class IssueRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insert (Incident incident) {
        jdbcTemplate.update("INSERT INTO incident (username, uniqid, incidentid, " +
                "incidentdate, description, latitude, longitude) VALUES (?,?,?,?,?,?,?)",
                new Object[] { incident.getUsername(), incident.getUniqid(), incident.getIncidentid(),
                incident.getIncidentdate(), incident.getDescription(),incident.getLatitude(),
                        incident.getLongitude()});

    }


    public List<Incident> findAll(){
        return jdbcTemplate.query("SELECT * FROM incident",
                (rs,rowNum)-> new Incident(rs.getString("username"),rs.getInt("uniqid"),
                        rs.getInt("incidentid"), rs.getDate("incidentdate"),
                        rs.getString("description"), rs.getFloat("latitude"),
                        rs.getFloat("longitude")));
    }

    public Integer selectIncidentID()  {
        String sql = "SELECT MAX (incidentid) FROM incident";
        return jdbcTemplate.queryForObject(sql,Integer.class);

    }



}

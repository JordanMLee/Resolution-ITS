package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.ResourceStatus;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

@SpringComponent
public class ResourceStatusRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // deployed resources
    public List<ResourceStatus>  getResourcesInUse(SqlParameterSource username){

        String sql = "SELECT d.resourceid, r.resourcename, i.description,r.username, d.startdate, d.returndate " +
                "FROM is_deployed_to d INNER JOIN resource r ON d.resourceid = r.resourceid " +
                "INNER JOIN incident i ON d.incidentid = i.incidentid WHERE i.username = (:username)";
        return namedParameterJdbcTemplate.query(sql, username, (rs,rowNum) -> new ResourceStatus(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("description"),rs.getString("username"),rs.getDate("startdate"),
                rs.getDate("returndate")));

    }
    //request made by me for other's resource
    public List<ResourceStatus> getMyRequestedResources(SqlParameterSource username){


        String sql1 = "SELECT re.resourceid, r.resourcename,i.description,r.username, re.returndate " +
                "FROM request re INNER JOIN resource r ON re.resourceid = r.resourceid " +
                "INNER JOIN incident i ON re.incidentid = i.incidentid " +
                "WHERE re.username = (:username)";


        return namedParameterJdbcTemplate.query(sql1, username,(rs,rowNum) -> new ResourceStatus(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("description"), rs.getString("username"),
                rs.getDate("returndate")));
    }

    public List<ResourceStatus> getResourceRequests(SqlParameterSource username) {


        String sql1 = "SELECT re.resourceid, r.resourcename,i.description,r.username, re.returndate " +
                "FROM request re INNER JOIN resource r ON re.resourceid = r.resourceid " +
                "INNER JOIN incident i ON re.incidentid = i.incidentid " +
                "WHERE r.username = (:username)";


        return namedParameterJdbcTemplate.query(sql1, username,(rs,rowNum) -> new ResourceStatus(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("description"), rs.getString("username"),
                rs.getDate("returndate")));

    }





}

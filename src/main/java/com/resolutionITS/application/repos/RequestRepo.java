package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Request;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringComponent
public class RequestRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //get request
    public Request getRequest(int resourceid,String username, int incidentid) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql="SELECT * FROM REQUEST WHERE resourceid = :resourceid AND username = :username AND incidentid = :incidentid";
        String sql1="SELECT * FROM REQUEST WHERE resourceid = ? AND username = ? AND incidentid = ?";

        return jdbcTemplate.queryForObject(sql1, new Object[] {resourceid, username, incidentid},
                new BeanPropertyRowMapper<>(Request.class));

    }

    //add new request
    public void insertRequest(Request request) {
        String sql = "INSERT INTO request (username, resourceid, incidentid, returndate)" +
                " VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,request.getUsername(),request.getResourceid(),
                request.getIncidentid(),request.getReturndate());

    };

    //reject resource request
    public void deleteRequest(int resourceid) {
        String sql = "DELETE FROM Request WHERE resourceid = ?";
        jdbcTemplate.update(sql,resourceid);
    }

    //deploy asset
    public void deployResource(Request request,Date startdate) {
        LocalDate now = LocalDate.now();
        String sql = "INSERT INTO is_deployed_to(resourceid,incidentid, returndate,startdate,deployeddate)" +
                " VALUES (?,?,?,?,?)" ;
        jdbcTemplate.update(sql,request.getResourceid(),request.getIncidentid(),request.getReturndate(),startdate,now);
    }

    //return resource
    public void returnResource(int resourceid){
        String sql = "DELETE FROM IS_DEPLOYED_TO WHERE RESOURCEID =?";
        jdbcTemplate.update(sql,resourceid);
    }

    // cancel request
    public void cancelRequest(Request request) {
        String sql = "DELETE FROM Request WHERE resourceid = ? and username = ?";
        jdbcTemplate.update(sql,request.getResourceid(),request.getUsername());
    }

}

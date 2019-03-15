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
    public Request getRequest(int technicianid, String username, int issueid) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT * FROM REQUEST WHERE technicianid = :technicianid AND username = :username AND issueid = :issueid";
        String sql1 = "SELECT * FROM REQUEST WHERE technicianid = ? AND username = ? AND issueid = ?";

        return jdbcTemplate.queryForObject(sql1, new Object[]{technicianid, username, issueid},
                new BeanPropertyRowMapper<>(Request.class));

    }

    //add new request
    public void insertRequest(Request request) {
        String sql = "INSERT INTO request (username, technicianid, issueid, returndate)" +
                " VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, request.getUsername(), request.gettechnicianid(),
                request.getissueid(), request.getReturndate());

    }

    //reject technician request
    public void deleteRequest(int technicianid) {
        String sql = "DELETE FROM Request WHERE technicianid = ?";
        jdbcTemplate.update(sql, technicianid);
    }

    //deploy asset
    public void deploytechnician(Request request, Date startdate) {
        LocalDate now = LocalDate.now();
        String sql = "INSERT INTO is_deployed_to(technicianid,issueid, returndate,startdate,deployeddate)" +
                " VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, request.gettechnicianid(), request.getissueid(), request.getReturndate(), startdate, now);
    }

    //return technician
    public void returntechnician(int technicianid) {
        String sql = "DELETE FROM IS_DEPLOYED_TO WHERE technicianID =?";
        jdbcTemplate.update(sql, technicianid);
    }

    // cancel request
    public void cancelRequest(Request request) {
        String sql = "DELETE FROM Request WHERE technicianid = ? and username = ?";
        jdbcTemplate.update(sql, request.gettechnicianid(), request.getUsername());
    }

}

package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.TechnicianStatus;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

@SpringComponent
public class TechnicianStatusRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // deployed technicians
    public List<TechnicianStatus> gettechniciansInUse(SqlParameterSource username) {

        String sql = "SELECT d.technicianid, r.technicianname, i.description,r.username, d.startdate, d.returndate " +
                "FROM is_deployed_to d INNER JOIN technician r ON d.technicianid = r.technicianid " +
                "INNER JOIN issue i ON d.issueid = i.issueid WHERE i.username = (:username)";
        return namedParameterJdbcTemplate.query(sql, username, (rs, rowNum) -> new TechnicianStatus(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("description"), rs.getString("username"), rs.getDate("startdate"),
                rs.getDate("returndate")));

    }

    //request made by me for other's technician
    public List<TechnicianStatus> getMyRequestedtechnicians(SqlParameterSource username) {


        String sql1 = "SELECT re.technicianid, r.technicianname,i.description,r.username, re.returndate " +
                "FROM request re INNER JOIN technician r ON re.technicianid = r.technicianid " +
                "INNER JOIN issue i ON re.issueid = i.issueid " +
                "WHERE re.username = (:username)";


        return namedParameterJdbcTemplate.query(sql1, username, (rs, rowNum) -> new TechnicianStatus(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("description"), rs.getString("username"),
                rs.getDate("returndate")));
    }

    public List<TechnicianStatus> gettechnicianRequests(SqlParameterSource username) {


        String sql1 = "SELECT re.technicianid, r.technicianname,i.description,r.username, re.returndate " +
                "FROM request re INNER JOIN technician r ON re.technicianid = r.technicianid " +
                "INNER JOIN issue i ON re.issueid = i.issueid " +
                "WHERE r.username = (:username)";


        return namedParameterJdbcTemplate.query(sql1, username, (rs, rowNum) -> new TechnicianStatus(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("description"), rs.getString("username"),
                rs.getDate("returndate")));

    }


}

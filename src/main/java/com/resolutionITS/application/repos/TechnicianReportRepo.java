package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.TechnicianReport;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
public class TechnicianReportRepo {
    // Run technician Report SQL
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<TechnicianReport> runReport() {
        return jdbcTemplate.query("SELECT r.skillid, e.skilldesc, r.technicianid FROM technician r INNER JOIN has_skill h ON r.technicianid = h.technicianid" +
                        " INNER JOIN skill e ON e.skillid = h.skillid",
                (rs, rowNum) -> new TechnicianReport(rs.getInt("skillid"), rs.getString("skilldesc"),
                        rs.getInt("technicianid")));
    }

    public List<TechnicianReport> runReport2() {
        return jdbcTemplate.query("SELECT e.skillid, e.skilldesc, h.technicianid FROM is_deployed_to d INNER JOIN has_skill h ON d.technicianid = h.technicianid" +
                        " INNER JOIN skill e ON e.skillid = h.skillid",
                (rs, rowNum) -> new TechnicianReport(rs.getInt("skillid"), rs.getString("skilldesc"),
                        rs.getInt("technicianid")));
    }


}

package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.ResourceReport;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
public class ResourceReportRepo {
    // Run Resource Report SQL
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ResourceReport> runReport() {
        return jdbcTemplate.query("SELECT r.esfid, e.esfdesc, r.resourceid FROM resource r INNER JOIN has_esf h ON r.resourceid = h.resourceid" +
                        " INNER JOIN esf e ON e.esfid = h.esfid",
                (rs, rowNum) -> new ResourceReport (rs.getInt("esfid" ), rs.getString("esfdesc"),
                        rs.getInt("resourceid")));
    }
    public List<ResourceReport> runReport2() {
        return jdbcTemplate.query("SELECT e.esfid, e.esfdesc, h.resourceid FROM is_deployed_to d INNER JOIN has_esf h ON d.resourceid = h.resourceid" +
                        " INNER JOIN esf e ON e.esfid = h.esfid",
                (rs, rowNum) -> new ResourceReport (rs.getInt("esfid" ), rs.getString("esfdesc"),
                        rs.getInt("resourceid")));
    }


}

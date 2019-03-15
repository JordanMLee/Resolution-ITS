package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.technician;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringComponent
public class CapabilitiesRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertCapabilities(technician technician) {
        int technicianid = technician.gettechnicianid();
        for (String capability : technician.getCapabilites()) {
            jdbcTemplate.update("INSERT INTO capability (technicianid, capability) VALUES (?,?)", technicianid, capability);
        }
    }

    public List<String> findBytechnicianId(int technicianid) {
        return jdbcTemplate.query("SELECT technicianid, capability FROM capability WHERE technicianid=?",
                new Object[]{technicianid}, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString("capability");
                    }
                });
    }

}

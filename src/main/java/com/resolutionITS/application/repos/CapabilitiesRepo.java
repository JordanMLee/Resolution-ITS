package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Resource;
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

    public void insertCapabilities(Resource resource){
        int resourceid = resource.getResourceid();
        for(String capability : resource.getCapabilites()) {
            jdbcTemplate.update("INSERT INTO capability (resourceid, capability) VALUES (?,?)", new Object[]{
                    resourceid, capability});
        }
    }

    public List<String> findByResourceId(int resourceid){
        return jdbcTemplate.query("SELECT resourceid, capability FROM capability WHERE resourceid=?",
                new Object[]{resourceid}, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString("capability");
                    }
                });
    }

}

package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Time;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringComponent
public class TimeRepo {
    @Autowired
    DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertTime(Time time) throws SQLException {
        Connection connection = dataSource.getConnection();

        String sql = "INSERT INTO time (\"value\", \"unit\") VALUES (?,?) RETURNING timeid; ";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setObject(1, time.getValue());
        stmt.setObject(2, time.getUnit());

        stmt.execute();
        ResultSet last_time_id = stmt.getResultSet();
        int time_id = 0;
        if (last_time_id.next()) {
            time_id = last_time_id.getInt(1);
        }

        return time_id;
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT \"timeid\", \"value\", \"unit\" FROM time",
                (rs, rowNum) -> new Time(rs.getInt("timeid"), rs.getString("unit"), rs.getDouble("value")));
    }
}

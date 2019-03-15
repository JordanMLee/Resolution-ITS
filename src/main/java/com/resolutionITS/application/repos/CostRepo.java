package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Cost;
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
public class CostRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    public int insertCost(Cost cost) throws SQLException {
        Connection connection = dataSource.getConnection();

        String sql = "INSERT INTO cost (\"value\", \"unit\") VALUES (?,?) RETURNING costid; ";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setObject(1, cost.getValue());
        stmt.setObject(2, cost.getUnit());

        stmt.execute();
        ResultSet last_cost_id = stmt.getResultSet();
        int cost_id = 0;
        if(last_cost_id.next()) {
            cost_id = last_cost_id.getInt(1);
        }

        return cost_id;
    }

    public List<Cost> findAll(){
        return jdbcTemplate.query("SELECT \"costid\", \"value\", \"unit\" FROM cost",
                (rs, rowNum) -> new Cost(rs.getInt("costid"),  rs.getString("unit"), rs.getDouble("value")));
    }
}

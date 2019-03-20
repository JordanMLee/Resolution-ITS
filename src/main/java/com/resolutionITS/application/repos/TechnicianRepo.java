package com.resolutionITS.application.repos;


import com.resolutionITS.application.entities.Issue;
import com.resolutionITS.application.entities.Time;
import com.resolutionITS.application.entities.technician;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringComponent
public class TechnicianRepo {

    @Autowired
    DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Integer selecttechnicianID() {
        String sql = "SELECT MAX (technicianid) FROM technician";
        return jdbcTemplate.queryForObject(sql, Integer.class);

    }

    public int inserttechnician(technician technician) throws SQLException {
        Connection connection = dataSource.getConnection();

        String sql = "INSERT INTO technician " +
                "(technicianid, username, technicianname, skillid, model, maxdist) " +
                "VALUES (DEFAULT, ?,?,?,?,?) RETURNING technicianid; ";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setObject(1, technician.getUsername());
        stmt.setObject(2, technician.gettechnicianname());
        stmt.setObject(3, technician.getskillid());
        stmt.setObject(4, technician.getModel());

        stmt.setObject(5, technician.getMaxdist());

        stmt.execute();
        ResultSet last_technician_id = stmt.getResultSet();
        //does not account for preloaded data!
        int technician_id = 17;
        if (last_technician_id.next()) {
            technician_id = last_technician_id.getInt(1);
        }

//        this.insertHastime(technician_id, technician.getTime().gettimeid());
        this.insertHastime(technician_id, technician.getTime().getCostid());

        return technician_id;
    }

    public int insertHasskill(int technicianid, short skillid) {
        return jdbcTemplate.update("INSERT INTO has_skill (technicianid, skillid) VALUES (?,?);", technicianid, skillid);
    }


    public int insertHastime(int technicianid, int timeid) {
        return jdbcTemplate.update("INSERT INTO has_time (technicianid, timeid) VALUES (?,?);", technicianid, timeid);
    }

    public List<technician> findAll() {
        return jdbcTemplate.query("SELECT technicianid, username, technicianname, skillid, model, " +
                        " maxdist FROM technician ",
                (rs, rowNum) -> new technician(rs.getInt("technicianid"), rs.getString("username"),
                        rs.getString("technicianname"), rs.getShort("skillid"), rs.getString("model"),
                        rs.getShort("maxdist"), null));
    }


    //v2 findAll()

    public List<technician> findAllv2() {
        Time time = new Time();

        String sql = "SELECT r.technicianid, r.technicianname, r.skillid, r.maxdist, r.model, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.technicianid = d.technicianid) as in_use, i.returndate as returndate " +
                "FROM technician r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_time hc on r.technicianid = hc.technicianid INNER JOIN time c " +
                "on hc.timeid = c.timeid LEFT JOIN IS_DEPLOYED_TO i on r.technicianid = i.technicianid";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new technician(
                        rs.getInt("technicianid"),
                        rs.getString("name"),
                        rs.getString("technicianname"),
                        (short) rs.getInt("skillid"),
                        rs.getString("model"),

                        (short) rs.getInt("maxdist"),
                        null, // capabilities
                        new Time(rs.getString("unit"), rs.getDouble("value")), // time
                        0, // time value
                        rs.getString("unit"),
                        rs.getBoolean("in_use"),
                        rs.getDate("returndate"),
                        null,
                        null
                ));

    }

    public List<technician> technicianSearch(String keyword, Integer skillid, Issue issue, Integer distanceInput) {
        Map<String, Object> params = new HashMap<String, Object>();

        String sql = "SELECT r.technicianid, r.technicianname, r.skillid, r.model, r.maxdist, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.technicianid = d.technicianid) as in_use, i.returndate as returndate ";

        if (issue != null) {
            sql += ", earth_distance(ll_to_earth(r., r.), ll_to_earth(:, :)) as distance ";

        }
        sql += "FROM technician r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_time hc on r.technicianid = hc.technicianid INNER JOIN time c " +
                "on hc.timeid = c.timeid LEFT JOIN IS_DEPLOYED_TO i on r.technicianid = i.technicianid";

        if (keyword != null || skillid != null || issue != null)
            sql += " WHERE ";

        if (keyword != null) {
            sql += " (LOWER(r.technicianname) ILIKE :keyword OR LOWER(r.model) ILIKE :keyword " +
                    "OR r.technicianid IN (SELECT technicianid FROM capability WHERE LOWER(capability) ILIKE :keyword))";
            params.put("keyword", "%" + keyword + "%");
        }

        if (skillid != null && keyword != null)
            sql += " AND ";

        if (skillid != null) {
            sql += " (r.skillid = (:skillid) OR r.technicianid in (SELECT technicianid FROM has_skill WHERE skillid = (:skillid)))";
            params.put("skillid", skillid);
        }

        if ((issue != null && keyword != null && distanceInput != null) || (issue != null && skillid != null && distanceInput != null))
            sql += " AND ";

        if (issue != null) {
            sql += " earth_distance(ll_to_earth(,)," +
                    " ll_to_earth(:,:)) < r.maxdist * 1000 " +
                    "AND earth_distance(ll_to_earth(,), " +
                    "ll_to_earth(:,:)) < :threshold_distance * 1000;";
            params.put("threshold_distance", distanceInput);
        }

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> new technician(
                rs.getInt("technicianid"),
                rs.getString("name"),
                rs.getString("technicianname"),
                (short) rs.getInt("skillid"),
                rs.getString("model"),

                (short) rs.getInt("maxdist"),
                null, // capabilities
                new Time(rs.getString("unit"), rs.getDouble("value")), // time
                0, // time value
                rs.getString("unit"),
                rs.getBoolean("in_use"),
                rs.getDate("returndate"),
                null,
                null
        ));
    }


    // v3 Search Functionality (deprecated)
    public List<technician> technicianSearch(SqlParameterSource keyword) {
        //technicianid | technicianname | ownername |  time   | unit | in_use | returndate
        /*RowMapper<List<technician>> technicianRowMapper = new RowMapper<List<technician>>() {
            List <technician> technicians;
            @Override
            public List<technician> mapRow(ResultSet resultSet, int i) throws SQLException {
                return technicians;
            }
        };*/
        String sql = "SELECT r.technicianid, r.technicianname, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.technicianid = d.technicianid) as in_use, i.returndate as returndate FROM technician r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_time hc on r.technicianid = hc.technicianid INNER JOIN time c " +
                "on hc.timeid = c.timeid LEFT JOIN IS_DEPLOYED_TO i on r.technicianid = i.technicianid " +
                "WHERE (LOWER(r.technicianname) LIKE LOWER(:keyword) OR LOWER(r.model) LIKE LOWER(:keyword) " +
                "OR r.technicianid IN (SELECT technicianid FROM capability WHERE LOWER(capability) LIKE LOWER(:keyword)))";
        return namedParameterJdbcTemplate.query(sql, keyword, (rs, rowNum) -> new technician(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("name"),
                rs.getInt("value"), rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }

    // skill search (deprecated)
    public List<technician> skillSearch(SqlParameterSource skillid) {
        String sql = "SELECT r.technicianid, r.technicianname, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.technicianid = d.technicianid) as in_use, i.returndate as returndate FROM technician r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_time hc on r.technicianid = hc.technicianid INNER JOIN time c " +
                "on hc.timeid = c.timeid LEFT JOIN IS_DEPLOYED_TO i on r.technicianid = i.technicianid " +
                "WHERE (r.skillid = (:skillid) OR r.technicianid in (SELECT technicianid FROM has_skill WHERE skillid = (:skillid)))";
        return namedParameterJdbcTemplate.query(sql, skillid, (rs, rowNum) -> new technician(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("name"),
                rs.getInt("value"), rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }
    //combining functionality of v2 and v3

    public List<technician> technicianSearchv2(SqlParameterSource keyword, SqlParameterSource skill) {
        //technicianid | technicianname | ownername |  time   | unit | in_use | returndate
        RowMapper<List<technician>> technicianRowMapper = new RowMapper<List<technician>>() {
            List<technician> technicians;

            @Override
            public List<technician> mapRow(ResultSet resultSet, int i) throws SQLException {
                return technicians;
            }
        };
        String sql = "SELECT r.technicianid, r.technicianname, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.technicianid = d.technicianid) as in_use, i.returndate as returndate FROM technician r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_time hc on r.technicianid = hc.technicianid INNER JOIN time c " +
                "on hc.timeid = c.timeid LEFT JOIN IS_DEPLOYED_TO i on r.technicianid = i.technicianid " +
                "WHERE (:keyword) IS NULL OR ((LOWER(r.technicianname) LIKE LOWER(:keyword) OR LOWER(r.model) LIKE LOWER(:keyword) " +
                "OR r.technicianid IN (SELECT technicianid FROM capability WHERE LOWER(capability) LIKE LOWER(:keyword))))" +
                "AND (:skill) IS NULL OR (1=1)";
        return namedParameterJdbcTemplate.query(sql, keyword, (rs, rowNum) -> new technician(rs.getInt("technicianid"),
                rs.getString("technicianname"), rs.getString("name"),
                rs.getInt("value"), rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }


    public void update(technician technician) {


        jdbcTemplate.update("UPDATE technician SET username=?, technicianname=?, " +
                        "skillid=?, model=?, maxdist=? WHERE technicianid=?",
                technician.getUsername(), technician.gettechnicianname(), technician.getskillid(), technician.getModel(),
                technician.getMaxdist(), technician.gettechnicianid());
    }


    // Run technician Report SQL

    public List<technician> runReport(Integer skill) {
        return jdbcTemplate.query("SELECT r.technicianid FROM technician r INNER JOIN has_skill h ON r.technicianid = h.technicianid" +
                        " INNER JOIN skill e ON e.skillid = h.skillid",
                (rs, rowNum) -> new technician(rs.getInt("technicianid")));
    }


}

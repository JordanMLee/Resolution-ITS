package com.resolutionITS.application.repos;


import com.resolutionITS.application.entities.Cost;
import com.resolutionITS.application.entities.Resource;
import com.resolutionITS.application.entities.Incident;


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
import java.util.*;

@SpringComponent
public class ResourceRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DataSource dataSource;

    public Integer selectResourceID()  {
        String sql = "SELECT MAX (resourceid) FROM resource";
        return jdbcTemplate.queryForObject(sql,Integer.class);

    }

    public int insertResource(Resource resource) throws SQLException {
        Connection connection = dataSource.getConnection();

        String sql = "INSERT INTO resource " +
                "(resourceid, username, resourcename, esfid, model, latitude, longitude, maxdist) " +
                "VALUES (DEFAULT, ?,?,?,?,?,?,?) RETURNING resourceid; ";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setObject(1, resource.getUsername());
        stmt.setObject(2, resource.getResourcename());
        stmt.setObject(3, resource.getEsfid());
        stmt.setObject(4, resource.getModel());
        stmt.setObject(5, resource.getLatitude());
        stmt.setObject(6, resource.getLongitude());
        stmt.setObject(7, resource.getMaxdist());

        stmt.execute();
        ResultSet last_resource_id = stmt.getResultSet();
        //does not account for preloaded data!
        int resource_id = 17;
        if(last_resource_id.next()) {
            resource_id = last_resource_id.getInt(1);
        }

        this.insertHasCost(resource_id, resource.getCost().getCostid());

        return resource_id;
    }

    public int insertHasESF(int resourceid, short esfid) {
        return jdbcTemplate.update("INSERT INTO has_esf (resourceid, esfid) VALUES (?,?);", resourceid, esfid);
    }


    public int insertHasCost(int resourceid, int costid) {
        return jdbcTemplate.update("INSERT INTO has_cost (resourceid, costid) VALUES (?,?);", resourceid, costid);
    }

    public List<Resource> findAll() {
        return jdbcTemplate.query("SELECT resourceid, username, resourcename, esfid, model, " +
                        "latitude, longitude, maxdist FROM resource ",
                (rs, rowNum) -> new Resource(rs.getInt("resourceid"), rs.getString("username"),
                        rs.getString("resourcename"), rs.getShort("esfid"),rs.getString("model"),
                        rs.getFloat("latitude"),rs.getFloat("longitude"), rs.getShort("maxdist"), null));
    }


    //v2 findAll()

    public List<Resource> findAllv2() {
        Cost cost = new Cost();

        String sql = "SELECT r.resourceid, r.resourcename, r.latitude, r.longitude, r.esfid, r.maxdist, r.model, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate " +
                "FROM RESOURCE r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c " +
                "on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid";

        return jdbcTemplate.query(sql,
                (rs,rowNum)->new Resource(
                        rs.getInt("resourceid"),
                        rs.getString("name"),
                        rs.getString("resourcename"),
                        (short) rs.getInt("esfid"),
                        rs.getString("model"),
                        (float) rs.getDouble("latitude"),
                        (float)rs.getDouble("longitude"),
                        (short)rs.getInt("maxdist"),
                        null, // capabilities
                        new Cost(rs.getString("unit"), rs.getDouble("value")), // cost
                        0, // cost value
                        rs.getString("unit"),
                        rs.getBoolean("in_use"),
                        rs.getDate("returndate"),
                        null,
                        null
                ));

    }

    public List<Resource> resourceSearch(String keyword, Integer esfid, Incident incident, Integer distanceInput) {
        Map<String, Object> params = new HashMap<String, Object>();

        String sql = "SELECT r.resourceid, r.resourcename, r.latitude, r.longitude, r.esfid, r.model, r.maxdist, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate ";

        if (incident != null) {
            sql += ", earth_distance(ll_to_earth(r.latitude, r.longitude), ll_to_earth(:latitude, :longitude)) as distance ";
            params.put("latitude", incident.getLatitude());
            params.put("longitude", incident.getLongitude());
        }
         sql += "FROM RESOURCE r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c " +
                "on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid";

        if(keyword != null || esfid != null || incident != null)
            sql += " WHERE ";

        if(keyword != null) {
            sql += " (LOWER(r.resourcename) ILIKE :keyword OR LOWER(r.model) ILIKE :keyword " +
                    "OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) ILIKE :keyword))";
            params.put("keyword", "%"+keyword+"%");
        }

        if (esfid != null && keyword != null)
            sql += " AND ";

        if (esfid != null) {
            sql += " (r.esfid = (:esfid) OR r.resourceid in (SELECT resourceid FROM has_esf WHERE esfid = (:esfid)))";
            params.put("esfid", esfid);
        }

        if ((incident != null && keyword != null && distanceInput !=null ) || (incident != null && esfid != null && distanceInput != null))
            sql += " AND ";

        if (incident != null) {
             sql += " earth_distance(ll_to_earth(latitude,longitude)," +
                     " ll_to_earth(:latitude,:longitude)) < r.maxdist * 1000 " +
                     "AND earth_distance(ll_to_earth(latitude,longitude), " +
                     "ll_to_earth(:latitude,:longitude)) < :threshold_distance * 1000;";
             params.put("threshold_distance", distanceInput);
        }

        return namedParameterJdbcTemplate.query(sql, params,(rs,rowNum)->new Resource(
                rs.getInt("resourceid"),
                rs.getString("name"),
                rs.getString("resourcename"),
                (short) rs.getInt("esfid"),
                rs.getString("model"),
                (float) rs.getDouble("latitude"),
                (float) rs.getDouble("longitude"),
                (short) rs.getInt("maxdist"),
                null, // capabilities
                new Cost(rs.getString("unit"), rs.getDouble("value")), // cost
                0, // cost value
                rs.getString("unit"),
                rs.getBoolean("in_use"),
                rs.getDate("returndate"),
                null,
                null
        ));
    }


    // v3 Search Functionality (deprecated)
    public List<Resource> resourceSearch(SqlParameterSource keyword) {
    //resourceid | resourcename | ownername |  cost   | unit | in_use | returndate
        /*RowMapper<List<Resource>> resourceRowMapper = new RowMapper<List<Resource>>() {
            List <Resource> resources;
            @Override
            public List<Resource> mapRow(ResultSet resultSet, int i) throws SQLException {
                return resources;
            }
        };*/
        String sql = "SELECT r.resourceid, r.resourcename, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c " +
                "on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid " +
                "WHERE (LOWER(r.resourcename) LIKE LOWER(:keyword) OR LOWER(r.model) LIKE LOWER(:keyword) " +
                "OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER(:keyword)))";
        return namedParameterJdbcTemplate.query(sql, keyword,(rs,rowNum)->new Resource(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("name"),
                rs.getInt("value"),rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }

    // esf search (deprecated)
    public List<Resource> esfSearch(SqlParameterSource esfid) {
        String sql = "SELECT r.resourceid, r.resourcename, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c " +
                "on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid " +
                "WHERE (r.esfid = (:esfid) OR r.resourceid in (SELECT resourceid FROM has_esf WHERE esfid = (:esfid)))";
        return namedParameterJdbcTemplate.query(sql, esfid, (rs,rowNum) -> new Resource(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("name"),
                rs.getInt("value"),rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }
    //combining functionality of v2 and v3

    public List<Resource> resourceSearchv2(SqlParameterSource keyword, SqlParameterSource esf) {
        //resourceid | resourcename | ownername |  cost   | unit | in_use | returndate
        RowMapper<List<Resource>> resourceRowMapper = new RowMapper<List<Resource>>() {
            List <Resource> resources;
            @Override
            public List<Resource> mapRow(ResultSet resultSet, int i) throws SQLException {
                return resources;
            }
        };
        String sql = "SELECT r.resourceid, r.resourcename, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO " +
                "as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN " +
                "USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c " +
                "on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid " +
                "WHERE (:keyword) IS NULL OR ((LOWER(r.resourcename) LIKE LOWER(:keyword) OR LOWER(r.model) LIKE LOWER(:keyword) " +
                "OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER(:keyword))))" +
                "AND (:esf) IS NULL OR (1=1)";
        return namedParameterJdbcTemplate.query(sql,keyword,(rs,rowNum)->new Resource(rs.getInt("resourceid"),
                rs.getString("resourcename"),rs.getString("name"),
                rs.getInt("value"),rs.getString("unit"), rs.getBoolean("in_use"),
                rs.getDate("returndate")));

    }


    public void update(Resource resource) {


        jdbcTemplate.update("UPDATE resource SET username=?, resourcename=?, " +
                        "esfid=?, model=?, latitude=?, longitude=?, maxdist=? WHERE resourceid=?",
                resource.getUsername(), resource.getResourcename(), resource.getEsfid(), resource.getModel(),
                resource.getLatitude(), resource.getLongitude(), resource.getMaxdist(), resource.getResourceid());
    }


    // Run Resource Report SQL

    public List<Resource> runReport(Integer esf) {
        return jdbcTemplate.query("SELECT r.resourceid FROM resource r INNER JOIN has_esf h ON r.resourceid = h.resourceid" +
                " INNER JOIN esf e ON e.esfid = h.esfid",
                (rs, rowNum) -> new Resource (rs.getInt("resourceid")));
    }



}

package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Issue;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
public class IssueRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Issue issue) {
        jdbcTemplate.update("INSERT INTO issue (username, uniqid, issueid, " +
                        "issuedate, description, latitude, longitude) VALUES (?,?,?,?,?,?,?)",
                issue.getUsername(), issue.getUniqid(), issue.getissueid(),
                issue.getissuedate(), issue.getDescription(), issue.getLatitude(),
                issue.getLongitude());

    }


    public List<Issue> findAll() {
        return jdbcTemplate.query("SELECT * FROM issue",
                (rs, rowNum) -> new Issue(rs.getString("username"), rs.getInt("uniqid"),
                        rs.getInt("issueid"), rs.getDate("issuedate"),
                        rs.getString("description"), rs.getFloat("latitude"),
                        rs.getFloat("longitude")));
    }

    public Integer selectissueID() {
        String sql = "SELECT MAX (issueid) FROM issue";
        return jdbcTemplate.queryForObject(sql, Integer.class);

    }


}

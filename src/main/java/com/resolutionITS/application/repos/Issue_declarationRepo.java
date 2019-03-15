package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Issue_declaration;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringComponent
public class Issue_declarationRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String selectuniqID(String declaration) {
        String sql = "SELECT uniqid FROM issue_declaration WHERE issueid = (SELECT MAX (issueid) FROM issue_declaration WHERE declaration = ?::declarations)";
        return jdbcTemplate.queryForObject(sql, new Object[]{declaration}, String.class);

    }

    public void insert(Issue_declaration issue_declaration) {
        jdbcTemplate.update("INSERT INTO issue_declaration (declaration, uniqid, issueid) VALUES (?::declarations,?,?)",
                issue_declaration.getDeclaration(), issue_declaration.getUniqid(),
                issue_declaration.getissueid());
    }
}

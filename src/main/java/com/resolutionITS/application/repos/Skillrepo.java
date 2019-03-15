package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Skill;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
//@UIScope
//@ViewScope
public class Skillrepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertskill(Skill skill) {

        jdbcTemplate.update("INSERT INTO skill (skillid, skilldesc) VALUES (?,?)", skill.getskillid(), skill.getskilldesc());
    }


    public List<Skill> findAll() {
        return jdbcTemplate.query("SELECT skillid, skilldesc FROM skill",
                (rs, rowNum) -> new Skill(rs.getInt("skillid"), rs.getString("skilldesc")));
    }


}

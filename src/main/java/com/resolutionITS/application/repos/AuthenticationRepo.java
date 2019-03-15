package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.Tier1;
import com.resolutionITS.application.entities.Tier2;
import com.resolutionITS.application.entities.Tier3;
import com.resolutionITS.application.entities.Users;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringComponent
public class AuthenticationRepo {

    @Autowired
    DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
    public String userAuth(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        Object[] input = new Object[] {username};
        String password = jdbcTemplate.queryForObject(query,input,String.class);
        return password;
    }*/

    /* Used to display User's information at login */
    public Users findUserByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(
                sql, new Object[]{username},
                new BeanPropertyRowMapper<>(Users.class));
    }

    public Tier1 findtier1(String username) {

        String sql = "SELECT * FROM tier1 WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{username},
                new BeanPropertyRowMapper<>(Tier1.class));

    }

    public Tier2 findtier2(String username) {
        String sql = "SELECT * FROM tier2 WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{username},
                new BeanPropertyRowMapper<>(Tier2.class)
        );


    }

    public Tier3 findGovt(String username) {
        String sql = "SELECT * FROM Govt WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{username},
                new BeanPropertyRowMapper<>(Tier3.class)
        );

    }


}

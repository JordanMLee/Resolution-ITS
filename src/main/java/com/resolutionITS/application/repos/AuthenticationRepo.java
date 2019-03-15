package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.*;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringComponent
public class AuthenticationRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    /*
    public String userAuth(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        Object[] input = new Object[] {username};
        String password = jdbcTemplate.queryForObject(query,input,String.class);
        return password;
    }*/

    /* Used to display User's information at login */
    public Users findUserByUsername(String username) {

        String sql = "SELECT * FROM Users WHERE username = ?";

        return jdbcTemplate.queryForObject(
                sql, new Object[] { username },
                new BeanPropertyRowMapper<>(Users.class));
    }

    public Company findCompany(String username) {

        String sql = "SELECT * FROM Company WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[] {username},
                new BeanPropertyRowMapper<>(Company.class));

    }

    public Municipality findMunicipality(String username) {
        String sql = "SELECT * FROM municipality WHERE username = ?";
        return  jdbcTemplate.queryForObject(
                sql, new Object[] {username},
                new BeanPropertyRowMapper<>(Municipality.class)
        );


    }

    public GovtImpl findGovt(String username) {
        String sql = "SELECT * FROM Govt WHERE username = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[] {username},
                new BeanPropertyRowMapper<>(GovtImpl.class)
        );

    }










}

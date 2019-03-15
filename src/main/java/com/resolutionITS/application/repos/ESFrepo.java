package com.resolutionITS.application.repos;

import com.resolutionITS.application.entities.ESF;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringComponent
//@UIScope
//@ViewScope
public class ESFrepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertESF(ESF esf){

        jdbcTemplate.update("INSERT INTO esf (esfid, esfdesc) VALUES (?,?)", new Object[] {
                esf.getEsfid(),esf.getEsfdesc()
        });
    }


    public List<ESF> findAll(){
        return jdbcTemplate.query("SELECT esfid, esfdesc FROM esf",
                (rs, rowNum) -> new ESF(rs.getInt("esfid"), rs.getString("esfdesc")));
    }



}

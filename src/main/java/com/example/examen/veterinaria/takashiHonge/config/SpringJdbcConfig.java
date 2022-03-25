package com.example.examen.veterinaria.takashiHonge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class SpringJdbcConfig {
    @Bean
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Veterinaria");
        dataSource.setUsername("postgres");
        dataSource.setPassword("tesla");
        return dataSource;
    }
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(){
        return  new SimpleJdbcInsert(postgresqlDataSource());
    }
    @Bean("sjc")
    public SimpleJdbcCall simpleJdbcCall(){
        return new SimpleJdbcCall(postgresqlDataSource());
    }

}

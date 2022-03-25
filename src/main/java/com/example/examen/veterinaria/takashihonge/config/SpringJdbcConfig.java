package com.example.examen.veterinaria.takashihonge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.examen.veterinaria.takashihonge")
public class SpringJdbcConfig {
    @Bean
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/db_veterinaria");
        dataSource.setUsername("postgres");
        dataSource.setPassword("tesla");
        return dataSource;
    }
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(){
        return  new SimpleJdbcInsert(postgresqlDataSource());
    }

}

package com.example.examen.veterinaria.takashiHonge.rowmapper;


import com.example.examen.veterinaria.takashiHonge.entity.Animal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AnimalRowMapper implements RowMapper<Animal> {

    @Override
    public Animal mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final Animal animal = new Animal();
        animal.setIdAnimal(resultSet.getInt("id_animal"));
        animal.setDuenho(resultSet.getString("duenho"));
        animal.setEdad(resultSet.getInt("edad"));
        animal.setPeso(resultSet.getInt("peso"));
        return animal;
    }
}

package com.example.examen.veterinaria.takashiHonge.rowmapper;

import com.example.examen.veterinaria.takashiHonge.entity.Veterinaria;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VeterinariaRowMapper implements RowMapper<Veterinaria> {

    @Override
    public Veterinaria mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final Veterinaria veterinaria = new Veterinaria();
        veterinaria.setIdVeterinaria(resultSet.getInt("id_veterinaria"));
        veterinaria.setDescripcion(resultSet.getString("descripcion"));
        veterinaria.setNombre(resultSet.getString("nombre"));
        return veterinaria;
    }
}

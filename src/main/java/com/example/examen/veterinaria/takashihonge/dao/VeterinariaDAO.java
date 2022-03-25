package com.example.examen.veterinaria.takashihonge.dao;

import com.example.examen.veterinaria.takashihonge.entity.Veterinaria;
import com.example.examen.veterinaria.takashihonge.rowmapper.VeterinariaRowMapper;
import com.example.examen.veterinaria.takashihonge.sqlerror.CustomSQLErrorCodeTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class VeterinariaDAO {

    private static final String SQL="SELECT * FROM veterinaria";
    private static final String SQL_INSERT = "INSERT INTO veterinaria (nombre, descripcion) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM veterinaria WHERE id_veterinaria=?";
    private static final String SQL_GET = "SELECT * FROM veterinaria WHERE id_veterinaria=?";
    private static final String SQL_MODIFY = "UPDATE veterinaria SET nombre=?, descripcion=? WHERE id_veterinaria=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    public List<Veterinaria> getAll() {
        return jdbcTemplate.query(SQL, new VeterinariaRowMapper());
    }

    public Veterinaria add(String nombre, String descripcion){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, descripcion);
            return preparedStatement;
        },keyHolder);
        Integer id = (Integer) keyHolder.getKeys()
                .entrySet().stream()
                .filter(m  -> m.getKey().equalsIgnoreCase("id_veterinaria"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        Veterinaria veterinaria = new Veterinaria();
        veterinaria.setIdVeterinaria(id);
        veterinaria.setNombre(nombre);
        veterinaria.setDescripcion(descripcion);
        return veterinaria;
    }

    public Veterinaria getVeterinaria(int id_veterinaria) {
        Veterinaria veterinaria = jdbcTemplate.queryForObject(SQL_GET, new Object[] { id_veterinaria }, new VeterinariaRowMapper());
        if(veterinaria!=null){
            return veterinaria;
        }else{
            return null;
        }
    }

    public int delete(int id_veterinaria) {
        return jdbcTemplate.update(SQL_DELETE, id_veterinaria);
    }

    public int modify(Veterinaria veterinaria){
        return this.jdbcTemplate.update(SQL_MODIFY,veterinaria.getNombre(),veterinaria.getDescripcion());
    }
}

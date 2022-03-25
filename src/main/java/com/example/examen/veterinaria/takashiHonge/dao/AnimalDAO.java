package com.example.examen.veterinaria.takashiHonge.dao;

import com.example.examen.veterinaria.takashiHonge.entity.Animal;
import com.example.examen.veterinaria.takashiHonge.rowmapper.AnimalRowMapper;
import com.example.examen.veterinaria.takashiHonge.sqlerror.CustomSQLErrorCodeTranslator;
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
public class AnimalDAO {
    private static final String SQL="SELECT * FROM animal";
    private static final String SQL_INSERT = "INSERT INTO animal (duenho, peso, edad) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM animal WHERE id_animal=?";
    private static final String SQL_GET = "SELECT * FROM animal WHERE id_animal = ?";
    private static final String SQL_MODIFY = "UPDATE animal SET =?, =? WHERE id_animal=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    public List<Animal> getAll() {
        return jdbcTemplate.query(SQL, new AnimalRowMapper());
    }
    
    public Animal add(String duenho, int peso, int edad){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, duenho);
            preparedStatement.setInt(2, peso);
            preparedStatement.setInt(3,edad);
            return preparedStatement;
        },keyHolder);
        Integer id = (Integer) keyHolder.getKeys()
                .entrySet().stream()
                .filter(m  -> m.getKey().equalsIgnoreCase("id_animal"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        Animal animal = new Animal();
        animal.setIdAnimal(id);
        animal.setDuenho(duenho);
        animal.setPeso(peso);
        animal.setEdad(edad);
        return animal;
    }

    public Animal getAnimal(int id_animal) {
        Animal animal = jdbcTemplate.queryForObject(SQL_GET, new Object[] { id_animal }, new AnimalRowMapper());
        if(animal!=null){
            return animal;
        }else{
            return null;
        }
    }
    


}

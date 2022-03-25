package com.example.examen.veterinaria.takashihonge.controller;

import com.example.examen.veterinaria.takashihonge.dao.AnimalDAO;
import com.example.examen.veterinaria.takashihonge.entity.Animal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalDAO animalDAO;
    @GetMapping("/listar")
    public String listaAlu(Model model) {
        List<Animal> animales = new ArrayList<>();
        animales = animalDAO.getAll();
        model.addAttribute("titulo", "Lista de Animales");
        model.addAttribute("id_animal", "ID Animal");
        model.addAttribute("duenho", "Dueño");
        model.addAttribute("peso", "Peso");
        model.addAttribute("animal", animales);
        return "animal-template/listar";
    }

    @GetMapping("/agregar")
    public String agregarAnimal(Model model) {
        Animal animal = new Animal();
        model.addAttribute("titulo", "Agregar Animal");
        model.addAttribute("animal", animal);
        model.addAttribute("error", new HashMap<>());
        return "animal-template/agregar";
    }

    @PostMapping("/agregar")
    public String agregarAnimalProc(@Valid Animal animal, BindingResult result, Model model,
                                 @RequestParam(name = "duenho") String duenho,
                                 @RequestParam(name = "peso") String peso,
                                    @RequestParam(name = "edad") String edad ) throws SQLException {

        if (duenho != "" && peso != "" && edad != "") {
            int intPeso = Integer.parseInt(peso);
            int intEdad = Integer.parseInt(edad);
            animal = animalDAO.add(duenho, intPeso, intEdad);
            model.addAttribute("id_animal", "ID Animal");
            model.addAttribute("duenho", "Dueño");
            model.addAttribute("peso", "Peso");
            model.addAttribute("edad","Edad");
            model.addAttribute("titulo", "Animal Agregado");
            model.addAttribute("animal", animal);

        } else if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "animal-template/agregar";
        }
        return "animal-template/resultado";
    }

    @GetMapping("/buscar")
    public String buscarAnimal(Model model) {
        Animal animal = new Animal();
        model.addAttribute("titulo", "Buscar Animal");
        model.addAttribute("animal", animal);
        model.addAttribute("error", new HashMap<>());
        return "animal-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarAnimalProc(@Valid Animal animal, BindingResult result, Model model,
                            @RequestParam(name= "id_animal") String idAnimal) throws SQLException {
        try{
            int idBuscar =Integer.parseInt(idAnimal);
            if (idBuscar>0) {
                try{
                    animal = animalDAO.getAnimal(idBuscar);
                    if(animal!=null){
                        model.addAttribute("id_animal", "ID Animal");
                        model.addAttribute("duenho", "Dueño");
                        model.addAttribute("peso", "Peso");
                        model.addAttribute("edad", "Edad");
                        model.addAttribute("titulo", "Animal Encontrado");
                        model.addAttribute("animal", animal);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Animal animalVacio =new Animal();
                    model.addAttribute("id_animal", " ");
                    model.addAttribute("duenho", " ");
                    model.addAttribute("peso", " ");
                    model.addAttribute("edad", " ");
                    model.addAttribute("titulo", "No se encuentra en la base de datos");
                    model.addAttribute("animal", animalVacio);
                    return "animal-template/resultado";
                }
                catch (NumberFormatException ex){
                    Map<String, String> errores = new HashMap<>();
                    result.getFieldErrors().forEach(err -> {
                        errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });
                    model.addAttribute("titulo", "Falta datos");
                    model.addAttribute("error", errores);
                    return "animal-template/buscar";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "animal-template/buscar";
            }

        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "animal-template/eliminar";
        }
        return "animal-template/resultado";
    }
    @GetMapping("/eliminar")
    public String eliminar(Model model) {
        Animal animal = new Animal();
        model.addAttribute("titulo", "Eliminar Animal");
        model.addAttribute("animal", animal);
        model.addAttribute("error", new HashMap<>());
        return "animal-template/eliminar";
    }

    @DeleteMapping("/eliminar")
    public String eliminarProc(@Valid Animal animal, BindingResult result, Model model,
                                  @RequestParam(name= "id_animal") String idAnimal) throws SQLException {
        try{
            int id =Integer.parseInt(idAnimal);
            if (id>0) {
                try{
                    animal = animalDAO.getAnimal(id);
                    if(animalDAO.delete(id)==1){
                        model.addAttribute("id_animal", "ID Animal");
                        model.addAttribute("duenho", "Dueño");
                        model.addAttribute("peso", "Peso");
                        model.addAttribute("edad", "Edad");
                        model.addAttribute("titulo", "Animal Eliminado");
                        model.addAttribute("animal", animal);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Animal animal1 =new Animal();
                    model.addAttribute("id_animal", " ");
                    model.addAttribute("duenho", " ");
                    model.addAttribute("peso", " ");
                    model.addAttribute("edad", " ");
                    model.addAttribute("titulo", "El animal no se encuentra en la base de dato");
                    model.addAttribute("animal", animal);
                    return "animal-template/resultado";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "animal-template/eliminar";
            }
        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "animal-template/eliminar";
        }
        return "animal-template/resultado";
    }
}

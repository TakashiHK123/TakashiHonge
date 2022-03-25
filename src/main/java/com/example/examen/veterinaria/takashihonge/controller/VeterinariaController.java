package com.example.examen.veterinaria.takashihonge.controller;

import com.example.examen.veterinaria.takashihonge.dao.VeterinariaDAO;
import com.example.examen.veterinaria.takashihonge.entity.Veterinaria;
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
@RequestMapping("/veterinaria")
public class VeterinariaController {
    
    @Autowired
    VeterinariaDAO veterinariaDAO;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Veterinaria> veterinarias = new ArrayList<>();
        veterinarias = veterinariaDAO.getAll();
        model.addAttribute("titulo", "Lista de Veterinariaes");
        model.addAttribute("id_veterinaria", "ID Veterinaria");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("descripcion", "Descripcion");
        model.addAttribute("veterinarias", veterinarias);
        return "veterinaria-template/listar";
    }

    @GetMapping("/agregar")
    public String agregarVeterinaria(Model model) {
        Veterinaria veterinaria = new Veterinaria();
        model.addAttribute("titulo", "Agregar Veterinaria");
        model.addAttribute("veterinaria", veterinaria);
        model.addAttribute("error", new HashMap<>());
        return "veterinaria-template/agregar";
    }

    @PostMapping("/agregar")
    public String agregarVeterinariaProc(@Valid Veterinaria veterinaria, BindingResult result, Model model,
                                    @RequestParam(name = "nombre") String nombre,
                                    @RequestParam(name = "descripcion") String descripcion) throws SQLException {

        if (nombre != "" && descripcion != "") {
            veterinaria = veterinariaDAO.add(nombre,descripcion);
            model.addAttribute("id_veterinaria", "ID Veterinaria");
            model.addAttribute("nombre", "Nombre");
            model.addAttribute("descripcion","Descripcion");
            model.addAttribute("titulo", "Veterinaria Agregado");
            model.addAttribute("veterinaria", veterinaria);

        } else if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "veterinaria-template/agregar";
        }
        return "veterinaria-template/resultado";
    }

    @GetMapping("/buscar")
    public String buscarVeterinaria(Model model) {
        Veterinaria Veterinaria = new Veterinaria();
        model.addAttribute("titulo", "Buscar Veterinaria");
        model.addAttribute("veterinaria", Veterinaria);
        model.addAttribute("error", new HashMap<>());
        return "veterinaria-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarVeterinariaProc(@Valid Veterinaria veterinaria, BindingResult result, Model model,
                                   @RequestParam(name= "id_veterinaria") String idVeterinaria) throws SQLException {
        try{
            int idBuscar =Integer.parseInt(idVeterinaria);
            if (idBuscar!=0) {
                try{
                    veterinaria = veterinariaDAO.getVeterinaria(idBuscar);
                    if(veterinaria!=null){
                        model.addAttribute("id_veterinaria", "ID Veterinaria");
                        model.addAttribute("descripcion", "Descripcion");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("titulo", "Veterinaria Encontrado");
                        model.addAttribute("veterinaria", veterinaria);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Veterinaria veterinariaVacio =new Veterinaria();
                    model.addAttribute("id_Veterinaria", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("descripcion", " ");
                    model.addAttribute("titulo", "No se encuentra en la base de datos");
                    model.addAttribute("veterinaria", veterinariaVacio);
                    return "veterinaria-template/resultado";
                }
                catch (NumberFormatException ex){
                    Map<String, String> errores = new HashMap<>();
                    result.getFieldErrors().forEach(err -> {
                        errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });
                    model.addAttribute("titulo", "Falta datos");
                    model.addAttribute("error", errores);
                    return "veterinaria-template/buscar";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "veterinaria-template/buscar";
            }

        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "veterinaria-template/buscar";
        }
        return "veterinaria-template/resultado";
    }
    @GetMapping("/eliminar")
    public String eliminar(Model model) {
        Veterinaria veterinaria = new Veterinaria();
        model.addAttribute("titulo", "Eliminar Veterinaria");
        model.addAttribute("veterinaria", veterinaria);
        model.addAttribute("error", new HashMap<>());
        return "veterinaria-template/eliminar";
    }

    @PostMapping("/eliminar")
    public String eliminarProc(@Valid Veterinaria veterinaria, BindingResult result, Model model,
                               @RequestParam(name= "id_veterinaria") String idVeterinaria) throws SQLException {
        try{
            int id =Integer.parseInt(idVeterinaria);
            if (id>0) {
                try{
                    veterinaria = veterinariaDAO.getVeterinaria(id);
                    if(veterinariaDAO.delete(id)==1){
                        model.addAttribute("id_Veterinaria", "ID Veterinaria");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("descripcion", "Descripcion");
                        model.addAttribute("titulo", "Veterinaria Eliminado");
                        model.addAttribute("veterinaria", veterinaria);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Veterinaria veterinaria1 =new Veterinaria();
                    model.addAttribute("id_veterinaria", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("descripcion", " ");
                    model.addAttribute("titulo", "El Veterinaria no se encuentra en la base de dato");
                    model.addAttribute("veterinaria", veterinaria);
                    return "veterinaria-template/resultado";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "veterinaria-template/eliminar";
            }
        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "veterinaria-template/eliminar";
        }
        return "veterinaria-template/resultado";
    }


}

package com.example.examen.veterinaria.takashihonge.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class Veterinaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Min(1)
    @Max(99999)
    private int idVeterinaria;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String descripcion;

    public Veterinaria() {
    }

    public Veterinaria(int idVeterinaria, String nombre, String descripcion) {
        this.idVeterinaria = idVeterinaria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdVeterinaria() {
        return idVeterinaria;
    }

    public void setIdVeterinaria(int idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Veterinaria{" +
                "idVeterinaria=" + idVeterinaria +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

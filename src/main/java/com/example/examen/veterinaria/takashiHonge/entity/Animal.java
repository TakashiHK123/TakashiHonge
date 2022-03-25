package com.example.examen.veterinaria.takashiHonge.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Max(1)
    @Min(99999)
    private int idAnimal;
    @NotNull
    @Max(1)
    @Min(99999)
    private int edad;
    @NotNull
    @Max(1)
    @Min(99999)
    private int peso;
    @NotEmpty
    private String duenho;

    public Animal() {
    }

    public Animal(int idAnimal, int edad, int peso, String duenho) {
        this.idAnimal = idAnimal;
        this.edad = edad;
        this.peso = peso;
        this.duenho = duenho;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getDuenho() {
        return duenho;
    }

    public void setDuenho(String duenho) {
        this.duenho = duenho;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", edad=" + edad +
                ", peso=" + peso +
                ", duenho='" + duenho + '\'' +
                '}';
    }
}

package com.example.sem10.services;

import com.example.sem10.domain.entities.Alumno;

import java.util.List;

public interface AlumnoService {

    void grabar(Alumno alumno);
    void eliminar(int id);
    Alumno buscar(Integer id);
    List<Alumno> listar();

}

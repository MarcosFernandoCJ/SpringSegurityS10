package com.example.sem10.services;

import com.example.sem10.domain.entities.Curso;

import java.util.List;

public interface CursoService {

    public void grabar(Curso curso);
    public void eliminar(int id);
    public Curso buscar(Integer id);
    public List<Curso> listar();

}

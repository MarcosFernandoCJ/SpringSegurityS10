package com.example.sem10.domain.persistence;

import com.example.sem10.domain.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoDao extends CrudRepository<Curso,Integer> {
}

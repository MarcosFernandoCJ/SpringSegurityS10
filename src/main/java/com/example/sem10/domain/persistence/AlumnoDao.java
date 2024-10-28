package com.example.sem10.domain.persistence;

import com.example.sem10.domain.entities.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface AlumnoDao extends CrudRepository<Alumno, Integer> {
}

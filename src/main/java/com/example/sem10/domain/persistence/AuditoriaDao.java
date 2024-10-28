package com.example.sem10.domain.persistence;

import com.example.sem10.domain.entities.Auditoria;
import org.springframework.data.repository.CrudRepository;


public interface AuditoriaDao extends CrudRepository<Auditoria, Integer> {

}


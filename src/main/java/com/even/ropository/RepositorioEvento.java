package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.Evento;

@Repository
public interface RepositorioEvento extends CrudRepository<Evento, Integer>{

}

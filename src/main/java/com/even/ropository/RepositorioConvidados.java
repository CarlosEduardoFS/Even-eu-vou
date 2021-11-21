package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.Convidado;

@Repository
public interface RepositorioConvidados extends CrudRepository<Convidado, Integer> {

}

package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.Pessoa;

@Repository
public interface RepositorioPessoas extends CrudRepository<Pessoa,Integer>{

}

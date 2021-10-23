package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.Produtos;

@Repository
public interface RepositorioProdutos extends CrudRepository<Produtos,Integer>{

}

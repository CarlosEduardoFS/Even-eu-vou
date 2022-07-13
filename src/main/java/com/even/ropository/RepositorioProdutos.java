package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.domain.Product;

@Repository
public interface RepositorioProdutos extends CrudRepository<Product,Integer>{

}

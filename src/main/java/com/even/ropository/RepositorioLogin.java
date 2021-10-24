package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.Login;

@Repository
public interface RepositorioLogin extends CrudRepository<Login,Integer>{
	
}

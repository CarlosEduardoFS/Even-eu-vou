package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.domain.Guest;

@Repository
public interface RepositorioConvidados extends CrudRepository<Guest, Integer> {

}

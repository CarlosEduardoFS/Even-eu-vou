package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.domain.Event;

@Repository
public interface RepositorioEvento extends CrudRepository<Event, Integer>{

}

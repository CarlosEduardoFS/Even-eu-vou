package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.InformacoesTecnicasEvento;

@Repository
public interface RepositorioInformacoesTecnicas extends CrudRepository<InformacoesTecnicasEvento,Integer>{

}

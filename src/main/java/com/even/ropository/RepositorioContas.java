package com.even.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.even.model.domain.Account;

@Repository
public interface RepositorioContas extends CrudRepository<Account,Integer> {

}

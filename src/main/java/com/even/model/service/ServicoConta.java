package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceConta;
import com.even.model.domain.Account;
import com.even.ropository.RepositorioContas;

@Service
public class ServicoConta implements InterfaceConta {

	@Autowired
	RepositorioContas bancoDados;

	@Override
	public void saveConta(Account conta) {

		bancoDados.save(conta);
	}

	@Override
	public List<Account> listarConta() {

		return (List<Account>) bancoDados.findAll();
	}

	@Override
	public void updateConta(Account conta) {
		bancoDados.save(conta);

	}

}

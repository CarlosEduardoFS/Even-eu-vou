package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceConta;
import com.even.model.Conta;
import com.even.ropository.RepositorioContas;

@Service
public class ServicoConta implements InterfaceConta {

	@Autowired
	RepositorioContas bancoDados;

	@Override
	public void saveConta(Conta conta) {

		bancoDados.save(conta);
	}

	@Override
	public List<Conta> listarConta() {

		return (List<Conta>) bancoDados.findAll();
	}

	@Override
	public void updateConta(Conta conta) {
		bancoDados.save(conta);

	}

}

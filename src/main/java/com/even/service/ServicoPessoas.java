package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfacePessoas;
import com.even.model.Pessoa;
import com.even.ropository.RepositorioPessoas;

@Service
public class ServicoPessoas implements InterfacePessoas {

	@Autowired
	RepositorioPessoas bancoDados;

	@Override
	public void savePessoas(Pessoa pessoa) {

		bancoDados.save(pessoa);

	}

	@Override
	public List<Pessoa> listarPessoas() {

		return (List<Pessoa>) bancoDados.findAll();
	}

	@Override
	public void updatePessoas(Pessoa pessoa) {
		bancoDados.save(pessoa);

	}

}

package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceLogin;
import com.even.model.domain.Login;
import com.even.ropository.RepositorioLogin;

@Service
public class ServicoLogin implements InterfaceLogin{

	@Autowired
	RepositorioLogin bancoDados;
	
	@Override
	public void saveLogin(Login login) {
		bancoDados.save(login);
		
	}

	@Override
	public List<Login> listarLogin() {
		return (List<Login>) bancoDados.findAll();
		
	}

	@Override
	public void atualizarEvento(Login login) {
		bancoDados.save(login);
		
	}

}

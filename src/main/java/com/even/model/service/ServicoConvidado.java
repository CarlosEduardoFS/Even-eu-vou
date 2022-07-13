package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceConvidado;
import com.even.model.domain.Guest;
import com.even.ropository.RepositorioConvidados;

@Service
public class ServicoConvidado implements InterfaceConvidado{
	
	@Autowired
	RepositorioConvidados banco;
	
	@Override
	public void saveConvidado(Guest convidado) {
		
		banco.save(convidado);
		
	}

	@Override
	public List<Guest> listarConvidado() {
		
		return (List<Guest>) banco.findAll();
	}

	@Override
	public void updateConvidado(Guest convidado) {
		banco.save(convidado);
		
	}
	
	

}

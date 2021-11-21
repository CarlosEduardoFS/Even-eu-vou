package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceConvidado;
import com.even.model.Convidado;
import com.even.ropository.RepositorioConvidados;

@Service
public class ServicoConvidado implements InterfaceConvidado{
	
	@Autowired
	RepositorioConvidados banco;
	
	@Override
	public void saveConvidado(Convidado convidado) {
		
		banco.save(convidado);
		
	}

	@Override
	public List<Convidado> listarConvidado() {
		
		return (List<Convidado>) banco.findAll();
	}

	@Override
	public void updateConvidado(Convidado convidado) {
		banco.save(convidado);
		
	}
	
	

}

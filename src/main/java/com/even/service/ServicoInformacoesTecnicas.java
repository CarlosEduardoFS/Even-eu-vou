package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceInformacoesTecnicas;
import com.even.model.InformacoesTecnicasEvento;
import com.even.ropository.RepositorioInformacoesTecnicas;

@Service
public class ServicoInformacoesTecnicas implements InterfaceInformacoesTecnicas{
	
	@Autowired
	RepositorioInformacoesTecnicas bancoDados;
	
	@Override
	public void saveInformacoes(InformacoesTecnicasEvento informacoes) {
		
		bancoDados.save(informacoes);
		
	}

	@Override
	public List<InformacoesTecnicasEvento> listarInformacoes() {
		
		return (List<InformacoesTecnicasEvento>) bancoDados.findAll();
	}

	@Override
	public void atualizarInformacoes(InformacoesTecnicasEvento informacoes) {
		bancoDados.save(informacoes);
		
	}

}

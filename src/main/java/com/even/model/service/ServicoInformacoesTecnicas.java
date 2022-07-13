package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceInformacoesTecnicas;
import com.even.model.domain.TechnicalInformationEvent;
import com.even.ropository.RepositorioInformacoesTecnicas;

@Service
public class ServicoInformacoesTecnicas implements InterfaceInformacoesTecnicas{
	
	@Autowired
	RepositorioInformacoesTecnicas bancoDados;
	
	@Override
	public void saveInformacoes(TechnicalInformationEvent informacoes) {
		
		bancoDados.save(informacoes);
		
	}

	@Override
	public List<TechnicalInformationEvent> listarInformacoes() {
		
		return (List<TechnicalInformationEvent>) bancoDados.findAll();
	}

	@Override
	public void atualizarInformacoes(TechnicalInformationEvent informacoes) {
		bancoDados.save(informacoes);
		
	}

}

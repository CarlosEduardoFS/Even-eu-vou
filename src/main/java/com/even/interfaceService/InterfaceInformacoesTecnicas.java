package com.even.interfaceService;

import java.util.List;

import com.even.model.domain.TechnicalInformationEvent;


public interface InterfaceInformacoesTecnicas {
	
	public void saveInformacoes(TechnicalInformationEvent informacoes);
	public List<TechnicalInformationEvent> listarInformacoes();
	public void atualizarInformacoes(TechnicalInformationEvent informacoes);

}

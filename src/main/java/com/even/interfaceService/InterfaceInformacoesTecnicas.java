package com.even.interfaceService;

import java.util.List;

import com.even.model.InformacoesTecnicasEvento;


public interface InterfaceInformacoesTecnicas {
	
	public void saveInformacoes(InformacoesTecnicasEvento informacoes);
	public List<InformacoesTecnicasEvento> listarInformacoes();
	public void atualizarInformacoes(InformacoesTecnicasEvento informacoes);

}

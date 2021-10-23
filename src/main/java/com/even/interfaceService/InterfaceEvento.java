package com.even.interfaceService;

import java.util.List;

import com.even.model.Evento;


public interface InterfaceEvento {
	
	public void saveEvento(Evento evento);
	public List<Evento> listarEvento();
	public void atualizarEvento(Evento evento);
}


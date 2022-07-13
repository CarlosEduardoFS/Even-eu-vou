package com.even.interfaceService;

import java.util.List;

import com.even.model.domain.Event;


public interface InterfaceEvento {
	
	public void saveEvento(Event evento);
	public List<Event> listarEvento();
	public void atualizarEvento(Event evento);
}


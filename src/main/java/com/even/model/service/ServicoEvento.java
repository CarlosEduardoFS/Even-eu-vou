package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceEvento;
import com.even.model.domain.Event;
import com.even.ropository.RepositorioEvento;

@Service
public class ServicoEvento implements InterfaceEvento {

	@Autowired
	RepositorioEvento bancoDados;
	
	@Override
	public void saveEvento(Event evento) {
		
		bancoDados.save(evento);
		
	}

	@Override
	public List<Event> listarEvento() {
		
		return (List<Event>) bancoDados.findAll();
	}

	@Override
	public void atualizarEvento(Event evento) {
		bancoDados.save(evento);
		
	}

}

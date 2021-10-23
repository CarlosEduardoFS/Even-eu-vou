package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceEvento;
import com.even.model.Evento;
import com.even.ropository.RepositorioEvento;

@Service
public class ServicoEvento implements InterfaceEvento {

	@Autowired
	RepositorioEvento bancoDados;
	
	@Override
	public void saveEvento(Evento evento) {
		
		bancoDados.save(evento);
		
	}

	@Override
	public List<Evento> listarEvento() {
		
		return (List<Evento>) bancoDados.findAll();
	}

	@Override
	public void atualizarEvento(Evento evento) {
		bancoDados.save(evento);
		
	}

}

package com.even.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceProdutos;
import com.even.model.Produtos;
import com.even.ropository.RepositorioProdutos;

@Service
public class ServicoProdutos implements InterfaceProdutos{
	
	@Autowired
	RepositorioProdutos bancoDados;
	
	@Override
	public void saveProdutos(Produtos produtos) {
		bancoDados.save(produtos);
		
	}

	@Override
	public List<Produtos> listarProdutos() {
		
		return (List<Produtos>) bancoDados.findAll();
	}

	@Override
	public void atualizarProdutos(Produtos produtos) {
		bancoDados.save(produtos);
		
	}

}

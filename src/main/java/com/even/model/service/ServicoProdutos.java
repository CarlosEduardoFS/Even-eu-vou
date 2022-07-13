package com.even.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.even.interfaceService.InterfaceProdutos;
import com.even.model.domain.Product;
import com.even.ropository.RepositorioProdutos;

@Service
public class ServicoProdutos implements InterfaceProdutos{
	
	@Autowired
	RepositorioProdutos bancoDados;
	
	@Override
	public void saveProdutos(Product produtos) {
		bancoDados.save(produtos);
		
	}

	@Override
	public List<Product> listarProdutos() {
		
		return (List<Product>) bancoDados.findAll();
	}

	@Override
	public void atualizarProdutos(Product produtos) {
		bancoDados.save(produtos);
		
	}

}

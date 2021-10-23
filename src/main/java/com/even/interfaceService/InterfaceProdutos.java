package com.even.interfaceService;

import java.util.List;

import com.even.model.Produtos;

public interface InterfaceProdutos {
	
	public void saveProdutos(Produtos produtos);
	public List<Produtos> listarProdutos();
	public void atualizarProdutos(Produtos produtos);

}

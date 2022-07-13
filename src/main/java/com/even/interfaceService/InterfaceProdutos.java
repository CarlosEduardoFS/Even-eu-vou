package com.even.interfaceService;

import java.util.List;

import com.even.model.domain.Product;

public interface InterfaceProdutos {
	
	public void saveProdutos(Product produtos);
	public List<Product> listarProdutos();
	public void atualizarProdutos(Product produtos);

}

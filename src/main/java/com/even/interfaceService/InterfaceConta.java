package com.even.interfaceService;

import java.util.List;

import com.even.model.Conta;


public interface InterfaceConta {
	
	public void saveConta(Conta conta);
	public List<Conta> listarConta();
	public void updateConta(Conta conta);
}

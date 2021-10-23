package com.even.interfaceService;

import java.util.List;

import com.even.model.Pessoa;

public interface InterfacePessoas {
	
	public void savePessoas(Pessoa pessoa);
	public List<Pessoa> listarPessoas();
	public void updatePessoas(Pessoa pessoa);

}

package com.even.interfaceService;

import java.util.List;

import com.even.model.domain.Account;


public interface InterfaceConta {
	
	public void saveConta(Account conta);
	public List<Account> listarConta();
	public void updateConta(Account conta);
}

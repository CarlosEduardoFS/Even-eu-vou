package com.even.interfaceService;

import java.util.List;

import com.even.model.Login;

public interface InterfaceLogin {
	
	public void saveLogin(Login login);
	public List<Login> listarLogin();
	public void atualizarEvento(Login login);

}

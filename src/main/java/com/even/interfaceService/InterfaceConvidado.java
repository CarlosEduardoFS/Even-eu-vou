package com.even.interfaceService;

import java.util.List;

import com.even.model.domain.Guest;

public interface InterfaceConvidado {
	
	public void saveConvidado(Guest convidado);
	public List<Guest> listarConvidado();
	public void updateConvidado(Guest convidado);

}

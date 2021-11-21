package com.even.interfaceService;

import java.util.List;

import com.even.model.Convidado;

public interface InterfaceConvidado {
	
	public void saveConvidado(Convidado convidado);
	public List<Convidado> listarConvidado();
	public void updateConvidado(Convidado convidado);

}

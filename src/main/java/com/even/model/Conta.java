package com.even.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeUsuario;
	
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn
	private Login login;
	
	@OneToMany
	@JoinColumn
	private List<Evento> eventos;
	
	public Conta() {
		
		eventos = new LinkedList<>();
		login = new Login();
		
	}
	
	public Conta(Integer id, String nomeUsario, List<Evento> eventos, Login login) {
		this.id = id;
		this.nomeUsuario = nomeUsario;
		this.eventos = eventos;
		this.login = login;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsario) {
		this.nomeUsuario = nomeUsario;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	} 

}

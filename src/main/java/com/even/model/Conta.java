package com.even.model;

import java.util.LinkedList;
import java.util.List;

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
	private String nomeUsario;
	
	@OneToOne
	@JoinColumn
	private Login login;
	
	@OneToOne
	@JoinColumn
	private Pessoa informacoesUsuario;
	
	@OneToMany
	@JoinColumn
	private List<Evento> eventos;
	
	public Conta() {
		
		eventos = new LinkedList<>();
		
	}
	
	public Conta(Integer id, String nomeUsario, Pessoa informacoesUsuario, List<Evento> eventos) {
		this.id = id;
		this.nomeUsario = nomeUsario;
		this.informacoesUsuario = informacoesUsuario;
		this.eventos = eventos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeUsario() {
		return nomeUsario;
	}

	public void setNomeUsario(String nomeUsario) {
		this.nomeUsario = nomeUsario;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Pessoa getInformacoesUsuario() {
		return informacoesUsuario;
	}

	public void setInformacoesUsuario(Pessoa informacoesUsuario) {
		this.informacoesUsuario = informacoesUsuario;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	} 

}

package com.even.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Convidado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	private String nome;
	private Boolean ativo;
	
	@OneToOne
	@JoinColumn
	private Produtos produto;
	
	@OneToOne
	@JoinColumn
	private Evento evento;

	public Convidado() {}

	public Convidado(Integer id, String nome, Produtos produto, Evento evento, Boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.produto = produto;
		this.evento = evento;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}	
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(nome);
		sb.append(evento.toString());
		sb.append(produto.toString());
		
		return sb.toString();
		
	}

}

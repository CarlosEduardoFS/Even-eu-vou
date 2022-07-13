package com.even.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	private String nome;
	private Integer quantidade;
	private Boolean ativo;

	@OneToOne
	@JoinColumn
	private Product produto;

	@OneToOne
	@JoinColumn
	private Event evento;

	public Guest() {
	}

	public Guest(Integer id, String nome, Product produto, Event evento, Boolean ativo, Integer quantidade) {
		this.id = id;
		this.nome = nome;
		this.produto = produto;
		this.evento = evento;
		this.ativo = ativo;
		this.quantidade = quantidade;
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

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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

package com.even.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Produtos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeProduto;
	private Integer quantidade;
	private Integer quantidadeConfirmada;
	private Boolean ativo;
	
	@OneToOne
	@JoinColumn
	private Evento evento;
	
	public Produtos() {
		evento = new Evento();
		quantidadeConfirmada = 0;
	}
	
	public Produtos(Integer id, String nomeProduto, Integer quantidade, Integer quantidadeConfirmada,Evento evento,Boolean ativo) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.quantidadeConfirmada = quantidadeConfirmada;
		this.evento = evento;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidadeConfirmada() {
		return quantidadeConfirmada;
	}

	public void setQuantidadeConfirmada(Integer quantidadeConfirmada) {
		this.quantidadeConfirmada += quantidadeConfirmada;
	}
	
	public boolean atingiuLimitie() {
		return quantidadeConfirmada == quantidade;
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

	public String todasInformacoes() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(nomeProduto+"\n");
		sb.append("Quantidade: "+quantidade);
		
		return sb.toString();
		
	}
	
}

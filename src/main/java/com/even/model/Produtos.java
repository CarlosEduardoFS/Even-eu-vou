package com.even.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produtos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeProduto;
	private Integer quantidade;
	private Integer quantidadeConfirmada;
	
	public Produtos() {}
	
	public Produtos(Integer id, String nomeProduto, Integer quantidade, Integer quantidadeConfirmada) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.quantidadeConfirmada = quantidadeConfirmada;
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
		this.quantidadeConfirmada = quantidadeConfirmada;
	}
	
	public String todasInformacoes() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(nomeProduto+"\n");
		sb.append("Quantidade: "+quantidade);
		
		return sb.toString();
		
	}
	
}

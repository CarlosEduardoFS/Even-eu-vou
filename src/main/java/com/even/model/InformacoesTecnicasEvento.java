package com.even.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class InformacoesTecnicasEvento {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long quantidadePessoas;
	private Long quantidadeMesas;
	private Long quantidadeCadeiras;
	
	@OneToMany
	@JoinColumn
	List<Produtos> produtos;
	
	public InformacoesTecnicasEvento() {
		produtos = new LinkedList<>();
	}
	
	public InformacoesTecnicasEvento(Integer id, Long quantidadePessoas, Long quantidadeMesas,
			Long quantidadeCadeiras, List<Produtos> produtos) {
		this.id = id;
		this.quantidadePessoas = quantidadePessoas;
		this.quantidadeMesas = quantidadeMesas;
		this.quantidadeCadeiras = quantidadeCadeiras;
		this.produtos = produtos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getQuantidadePessoas() {
		return quantidadePessoas;
	}

	public void setQuantidadePessoas(Long quantidadePessoas) {
		this.quantidadePessoas = quantidadePessoas;
	}

	public Long getQuantidadeMesas() {
		return quantidadeMesas;
	}

	public void setQuantidadeMesas(Long quantidadeMesas) {
		this.quantidadeMesas = quantidadeMesas;
	}

	public Long getQuantidadeCadeiras() {
		return quantidadeCadeiras;
	}

	public void setQuantidadeCadeiras(Long quantidadeCadeiras) {
		this.quantidadeCadeiras = quantidadeCadeiras;
	}

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
	}
	
	public String todasInformacoes() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Quantidade de pessoas: "+quantidadePessoas+"\n");
		sb.append("Quantidade de mesas: "+quantidadeMesas+"\n");
		sb.append("Quantidade de cadeiras: "+quantidadeCadeiras+"\n");
		
		return sb.toString();
		
	}
	
}

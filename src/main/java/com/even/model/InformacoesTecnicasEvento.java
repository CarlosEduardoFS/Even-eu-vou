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
public class InformacoesTecnicasEvento {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long quantidadePessoas;
	private Long quantidadeMesas;
	private Long quantidadeCadeiras;
	
	@OneToOne
	@JoinColumn
	private Evento evento;
	
	@OneToMany (cascade = CascadeType.PERSIST)
	@JoinColumn
	List<Produtos> produtos;
	
	public InformacoesTecnicasEvento() {
		produtos = new LinkedList<>();
	}
	
	public InformacoesTecnicasEvento(Integer id, Long quantidadePessoas, Long quantidadeMesas,
			Long quantidadeCadeiras, List<Produtos> produtos,Evento evento ) {
		this.id = id;
		this.quantidadePessoas = quantidadePessoas;
		this.quantidadeMesas = quantidadeMesas;
		this.quantidadeCadeiras = quantidadeCadeiras;
		this.produtos = produtos;
		this.evento = evento;
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
		setQuantidadeMesas();
		return quantidadeMesas;
	}

	private void setQuantidadeMesas() {
		this.quantidadeMesas = (Long)quantidadePessoas/4;
	}

	public Long getQuantidadeCadeiras() {
		setQuantidadeCadeiras();
		return quantidadeCadeiras;
	}

	private void setQuantidadeCadeiras() {
		this.quantidadeCadeiras = quantidadePessoas + 10;
	}

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
	}
	
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String todasInformacoes() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Quantidade de pessoas: "+quantidadePessoas+"\n");
		sb.append("Quantidade de mesas: "+quantidadeMesas+"\n");
		sb.append("Quantidade de cadeiras: "+quantidadeCadeiras+"\n");
		
		return sb.toString();
		
	}
	
}

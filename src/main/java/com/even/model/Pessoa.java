package com.even.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomePessoa;
	private Byte idade;
	
	public Pessoa() {}
	
	public Pessoa(String nomePessoa, Byte idade) {
		this.nomePessoa = nomePessoa;
		this.idade = idade;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Byte getIdade() {
		return idade;
	}

	public void setIdade(Byte idade) {
		this.idade = idade;
	}

}

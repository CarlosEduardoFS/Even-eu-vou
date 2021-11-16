package com.even.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeEvento;
	private Date dataEvento;
	private String descricao;
	private Boolean convidadosLevamProdutos;
	private Boolean precisaDeProdutos;
	private Boolean ativo;
	
	@OneToOne
	@JoinColumn
	private Conta organizador;
	
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn
	private InformacoesTecnicasEvento informacoes;
	
	public Evento() {
		informacoes = new InformacoesTecnicasEvento();
	}
	
	public Evento(Integer id, String nomeEvento, Date dataEvento, String descricao,  InformacoesTecnicasEvento informacoes,Boolean convidadosLevamProdutos, Boolean precisaDeProdutos,Boolean ativo) {
		this.id = id;
		this.nomeEvento = nomeEvento;
		this.dataEvento = dataEvento;
		this.descricao = descricao;
		this.informacoes = informacoes;
		this.convidadosLevamProdutos = convidadosLevamProdutos;
		this.precisaDeProdutos = precisaDeProdutos;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Conta organizador) {
		this.organizador = organizador;
	}
	
	public InformacoesTecnicasEvento getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(InformacoesTecnicasEvento informacoes) {
		this.informacoes = informacoes;
	}
	
	public Boolean getConvidadosLevamProdutos() {
		return convidadosLevamProdutos;
	}

	public void setConvidadosLevamProdutos(Boolean convidadosLevamProdutos) {
		this.convidadosLevamProdutos = convidadosLevamProdutos;
	}

	public Boolean getPrecisaDeProdutos() {
		return precisaDeProdutos;
	}

	public void setPrecisaDeProdutos(Boolean precisaDeProdutos) {
		this.precisaDeProdutos = precisaDeProdutos;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String todasInformacoes() {
		
		Calendar calendario = Calendar.getInstance();
		
		calendario.setTime(dataEvento);
		
		
		String data = Calendar.DAY_OF_MONTH +"/"+ Calendar.MONTH+"/"+Calendar.YEAR;
		String hora = Calendar.HOUR+":"+Calendar.MINUTE+":"+Calendar.SECOND;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(nomeEvento + "\n");
		sb.append("Dia: "+data + "\n");
		sb.append("Hora: "+hora+"/n");
		sb.append("Descrição: "+descricao+"\n");
		sb.append("Organizador: "+organizador.getNomeUsuario());
		sb.append("Contato: "+organizador.getLogin().getEmail());
		
		return sb.toString();
		
	}

}

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
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeEvento;
	private String dataEvento;
	private String horaEvento;
	private String descricao;
	private Boolean convidadosLevamProdutos;
	private Boolean precisaDeProdutos;
	private Boolean ativo;
	private String chaveBusca;

	@OneToOne
	@JoinColumn
	private Conta organizador;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	private InformacoesTecnicasEvento informacoes;

	@OneToMany
	@JoinColumn
	private List<Convidado> convidados;

	public Evento() {
		informacoes = new InformacoesTecnicasEvento();
		convidados = new LinkedList<>();
	}

	public Evento(Integer id, String nomeEvento, String dataEvento, String horaEvento, String descricao,
			Boolean convidadosLevamProdutos, Boolean precisaDeProdutos, Boolean ativo, String chaveBusca,
			Conta organizador, InformacoesTecnicasEvento informacoes, List<Convidado> convidados) {
		this.id = id;
		this.nomeEvento = nomeEvento;
		this.dataEvento = dataEvento;
		this.horaEvento = horaEvento;
		this.descricao = descricao;
		this.convidadosLevamProdutos = convidadosLevamProdutos;
		this.precisaDeProdutos = precisaDeProdutos;
		this.ativo = ativo;
		this.chaveBusca = chaveBusca;
		this.organizador = organizador;
		this.informacoes = informacoes;
		this.convidados = convidados;
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

	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(String horaEvento) {
		this.horaEvento = horaEvento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getChaveBusca() {
		return chaveBusca;
	}

	public void setChaveBusca(String chaveBusca) {
		this.chaveBusca = chaveBusca;
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

	public List<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}

	public String todasInformacoes() {

		StringBuilder sb = new StringBuilder();

		sb.append(nomeEvento + "\n");
		sb.append("Dia: " + dataEvento + "\n");
		sb.append("Hora: " + horaEvento + "/n");
		sb.append("Descrição: " + descricao + "\n");
		sb.append("Organizador: " + organizador.getNomeUsuario());
		sb.append("Contato: " + organizador.getLogin().getEmail());

		return sb.toString();

	}

}

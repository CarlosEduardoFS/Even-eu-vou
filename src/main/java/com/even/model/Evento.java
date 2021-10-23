package com.even.model;

import java.util.Calendar;
import java.util.Date;

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
	
	@OneToOne
	@JoinColumn
	private Conta organizador;
	
	public Evento() {}
	
	public Evento(Integer id, String nomeEvento, Date dataEvento, String descricao) {
		this.id = id;
		this.nomeEvento = nomeEvento;
		this.dataEvento = dataEvento;
		this.descricao = descricao;
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
		sb.append("Organizador: "+organizador.getNomeUsario());
		sb.append("Contato: "+organizador.getEmail());
		
		return sb.toString();
		
	}

}

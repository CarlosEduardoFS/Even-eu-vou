package com.even.model.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userName;
	
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn
	private Login login;
	
	@OneToMany (cascade = CascadeType.PERSIST)
	@JoinColumn
	private List<Event> events;
	
	public Account() {
		
		events = new LinkedList<>();
		login = new Login();
		
	}
	
	public Account(Integer id, String userName, List<Event> events, Login login) {
		this.id = id;
		this.userName = userName;
		this.events = events;
		this.login = login;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEventos(List<Event> events) {
		this.events = events;
	} 
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(id, other.id);
	}

	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nNome usuario: "+userName+"\n");
		sb.append("Email: "+login.getEmail());
		sb.append("Senha: "+login.getSenha());
		
		return sb.toString();
		
	}

}

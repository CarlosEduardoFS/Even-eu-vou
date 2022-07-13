package com.even.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Login implements Comparable<Login> {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String senha;
	
	public Login() {}
	
	public Login(Integer id, String email, String senha) {
		this.id = id;
		this.email = email;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int compareTo(Login o) {
		
		if ( email.compareTo(o.getEmail())> 0) {
			
			return 1;
			
		}else if (email.compareTo(o.getEmail()) == 0) {
			
			return 0;
		}else {
			
			return -1;
		}
	}

}

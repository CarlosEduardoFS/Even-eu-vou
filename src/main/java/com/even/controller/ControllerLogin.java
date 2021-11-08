package com.even.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Login;
import com.even.service.ServicoConta;
import com.even.service.ServicoLogin;

@Controller
@RequestMapping
public class ControllerLogin {

	@Autowired
	ServicoLogin banco;
	
	@Autowired
	ServicoConta bancoConta;
	
	@PostMapping("/salvarLogin")
	public String salvarLogin(@ModelAttribute Login login) {
		
		banco.saveLogin(login);
		
		return "login";
	}
	
	@GetMapping("/login")
	public ModelAndView Login() {
		
		ModelAndView mv = new ModelAndView("login");
		Login login = new Login();
		mv.addObject("login", login);
		
		return mv;
		
	}
	
	@PostMapping("/verificaConta")
	public ModelAndView verificaConta(@ModelAttribute Login login) {
		
		List<Login> logins = banco.listarLogin();
		
		Collections.sort(logins);
		
		int posicao = Collections.binarySearch(logins, login, null);
		
		if (posicao >= 0) {
			
			Login loginBanco = logins.get(posicao);
			
			if (isLogin(loginBanco, login)) {
				Conta contaLog = contaLogada(loginBanco.getId());
				
				ModelAndView mv = new ModelAndView("telaUsuario");
				mv.addObject("conta", contaLog);
						
				return mv;
				
			}	
		}
		
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	private Conta contaLogada(int id) {
		
		Conta conta = new Conta();
		
		for (Conta conta2 : bancoConta.listarConta()) {
			
			Login login2 = conta2.getLogin();
			
			if (login2.getId() == id) {
				
				conta = conta2;
				
			}
		}
		
		return conta;
		
	}
	
	private boolean isLogin(Login loginBanco, Login loginInformado) {
		
		if (verificaEmail(loginBanco, loginInformado) && loginBanco.getSenha().equals(loginInformado.getSenha()))
			return true;
		
		return false;
		
	}
	
	@GetMapping("/atualizarSenha")
	public ModelAndView atualizarSenha() {
		
		ModelAndView mv = new ModelAndView("util/alterarSenha");
		Login login = new Login();
		mv.addObject("login", login);
		
		return mv;
	}
	
	@PostMapping("/verificaEmail")
	public ModelAndView atualizarSenha(@ModelAttribute Login login) {
		
		List<Login> logins = banco.listarLogin();
		
		
		Collections.sort(logins);
		
		int posicao = Collections.binarySearch(logins, login, null);
		
		if (posicao >= 0) {
			
			Login loginBanco = logins.get(posicao);
			
			if (verificaEmail(loginBanco, login)) {
				ModelAndView mv = new ModelAndView("util/altSenha");
				login.setId(loginBanco.getId());
				mv.addObject("login", login);
				return mv;
			}

		}
		ModelAndView mv = new ModelAndView("util/atualizarSenha");
		
		
		return mv;
	}

	private boolean verificaEmail(Login loginBanco,Login loginInformado) {
		
		if (loginBanco.getEmail().equals(loginInformado.getEmail())) {
			return true;
			
		}
		
		return false;
	}
	
	
}

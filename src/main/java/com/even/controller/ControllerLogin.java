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

import com.even.model.Login;
import com.even.service.ServicoLogin;

@Controller
@RequestMapping
public class ControllerLogin {

	@Autowired
	ServicoLogin banco;
	
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
	public String verificaConta(@ModelAttribute Login login) {
		
		List<Login> logins = banco.listarLogin();
		
		Collections.sort(logins);
		
		int posicao = Collections.binarySearch(logins, login, null);
		
		if (posicao >= 0) {
			
			Login loginBanco = logins.get(posicao);
			
			if (isLogin(loginBanco, login))
				return "telaUsuario";
			
		}
		
		return "login";
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
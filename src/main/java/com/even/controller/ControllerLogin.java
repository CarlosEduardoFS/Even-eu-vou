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
				return "entrou";
			
		}
		
		return "login";
	}
	
	private boolean isLogin(Login loginBanco, Login loginInformado) {
		
		if (loginBanco.getEmail().equals(loginInformado.getEmail()) && loginBanco.getSenha().equals(loginInformado.getSenha()))
			return true;
		
		return false;
		
	}
	
	
}

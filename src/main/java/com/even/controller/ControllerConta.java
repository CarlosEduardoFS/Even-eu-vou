package com.even.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.service.ServicoConta;

@Controller
@RequestMapping
public class ControllerConta {
	
	@Autowired
	ServicoConta banco;
	
	public ModelAndView cadastro() {
		
		ModelAndView mv = new ModelAndView("conta/cadastro");
		Conta conta = new Conta();
		mv.addObject("conta", conta);
		
		return mv;
		
	}
	
	public String salvarConta (Conta conta) {
		
		banco.saveConta(conta);
		
		return "conta/login";
		
	}	
}

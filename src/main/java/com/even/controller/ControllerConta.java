package com.even.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.service.ServicoConta;

@Controller
@RequestMapping
public class ControllerConta {
	
	@Autowired
	ServicoConta banco;

	@GetMapping("/cadastro")
	public ModelAndView cadastro() {
		
		ModelAndView mv = new ModelAndView("cadastro");
		Conta conta = new Conta();
		mv.addObject("conta", conta);
		
		return mv;
		
	}
	
	@PostMapping("/salvarConta")
	public String salvarConta (@ModelAttribute Conta conta) {
		
		banco.saveConta(conta);
		
		return "login";
		
	}
}

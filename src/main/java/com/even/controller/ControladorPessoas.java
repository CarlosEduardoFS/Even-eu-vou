package com.even.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.even.model.Pessoa;
import com.even.service.ServicoPessoas;

@Controller
@RequestMapping
public class ControladorPessoas {
	
	@Autowired
	private ServicoPessoas servico;
	
	@GetMapping("/listarPessoas")
	public String listarPessoas(Model model) {
		
		List<Pessoa> lista =  servico.listarPessoas();
		
		model.addAttribute("lista", lista);
		
		return "index";
	}

}

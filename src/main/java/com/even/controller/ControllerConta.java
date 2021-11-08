package com.even.controller;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Evento;
import com.even.model.Produtos;
import com.even.service.ServicoConta;
import com.even.service.ServicoEvento;
import com.even.service.ServicoProdutos;

@Controller
@RequestMapping
public class ControllerConta {
	
	@Autowired
	ServicoConta banco;
	
	Conta contaLogada;
	
	@Autowired
	ServicoProdutos bancoProduto;
	
	@Autowired
	ServicoEvento bancoEvento;


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
	
	@GetMapping("/cadastroEvento/{id}")
	public ModelAndView criarEvento(@PathVariable("id") Integer id) {
		
		Conta conta = new Conta();
		List<Conta> list = banco.listarConta();
		conta = list.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		
		contaLogada = new Conta();
		contaLogada = conta;
		
		ModelAndView mv = new ModelAndView("cadastroEvento");
		Evento evento = new Evento();
		
		evento.setOrganizador(conta);
		mv.addObject("evento", evento);
		
		return mv;
		
	}
	
	@PostMapping("/cadastroEvento")
	public String salvarEvento(@ModelAttribute Evento evento) {
		
		
		List<Conta> list = banco.listarConta();
		int idConta = evento.getOrganizador().getId();
		Conta conta = list.stream().filter(x -> x.getId() == idConta).findFirst().orElse(null);
		
		//List<Evento> listEve = new LinkedList<>();
		//listEve.add(evento);
		
		bancoEvento.saveEvento(evento);
		
		List<Evento> listEve = new LinkedList<>();
		for(Evento evento2 : bancoEvento.listarEvento()) {
			
			if (evento2.getOrganizador().getId() == contaLogada.getId()) {
				listEve.add(evento);
			}
		}
		
		listEve.add(evento);
		
		conta.setEventos(listEve);
		
		banco.saveConta(conta);
		
		
		
		
		return "cadastroProduto";
		
	}
	
	@GetMapping("/cadastroProduto")
	public ModelAndView cadastroProduto() {
		
		ModelAndView mv = new ModelAndView("cadastroProduto");
		Produtos produto = new Produtos();
		mv.addObject("produto", produto);
		mv.addObject("produtos", bancoProduto.listarProdutos());

		return mv;
	}
	
	@PostMapping("/salvarProduto")
	public String salvarProduto(@ModelAttribute Produtos produto) {
		
		List<Produtos> produtos = new LinkedList<>();
		
		bancoProduto.saveProdutos(produto);
		
		for (Produtos produtos2 : bancoProduto.listarProdutos()) {
			
			
			
		}
		
		
		
		return "cadastroProduto";
	}
	

}

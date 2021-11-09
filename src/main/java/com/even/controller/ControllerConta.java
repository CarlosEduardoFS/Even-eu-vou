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
	
	@GetMapping("/telaUsuario/{id}")
	public String telaUsuario(@PathVariable("id") Integer id) {
		
		Conta conta = new Conta();
		List<Conta> list = banco.listarConta();
		conta = list.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		
		contaLogada = new Conta();
		contaLogada = conta;
		
		System.out.println(contaLogada.toString());
		
		return "telaUsuario";
	}
	
	@GetMapping("/telaUso")
	public String telaUsuario() {
		
		return "telaUsuario";
	}
	
	
	@GetMapping("/cadastroEvento")
	public ModelAndView criarEvento() {
		
		ModelAndView mv = new ModelAndView("cadastroEvento");
		Evento evento = new Evento();
		
		evento.setOrganizador(contaLogada);
		mv.addObject("evento", evento);
		
		return mv;
		
	}
	
	@PostMapping("/cadastroEvento")
	public ModelAndView salvarEvento(@ModelAttribute Evento evento) {
		
		ModelAndView mv = new ModelAndView("redirecionamentos/redireInformacoes");
		
		List<Conta> list = banco.listarConta();
		int idConta = evento.getOrganizador().getId();
		Conta conta = list.stream().filter(x -> x.getId() == idConta).findFirst().orElse(null);
		
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
		
		Evento evento2 = new Evento();
		evento2.setId(evento.getId());
		
		mv.addObject("evento", evento2);
		
		return mv;
		
	}
	
	@GetMapping("/cadastroProduto/{id}")
	public ModelAndView cadastroProduto(@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView("cadastroProduto");
		
		List<Evento> list = bancoEvento.listarEvento();
		List<Produtos> list2 = new LinkedList<>();
		
		Produtos produto = new Produtos();
		Evento evento = list.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		produto.setEvento(evento);
		
		for (Produtos produ : bancoProduto.listarProdutos()) {
			
			if(produ.getEvento().getId() == produto.getEvento().getId()) {
				
				list2.add(produ);
				
			}
			
		}
		
		
		mv.addObject("produto1", produto);
		mv.addObject("produtos", list2);

		return mv;
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvarProduto(@ModelAttribute Produtos produto) {
		
		ModelAndView mv = new ModelAndView("cadastroProduto");
		
		List<Evento> list = bancoEvento.listarEvento();
		List<Produtos> produtos = new LinkedList<>();
		List<Produtos> list2 = new LinkedList<>();
		
		for (Produtos produtos2 : bancoProduto.listarProdutos()) {
			
			if ((produto.getEvento().getId() == produtos2.getEvento().getId()) && (produtos2 != null)) {
				produtos.add(produtos2);
			}	
			
		}
		produtos.add(produto);
		
		bancoProduto.saveProdutos(produto);
		
		Produtos produto2 = new Produtos();
		Evento evento = list.stream().filter(x -> x.getId() == produto.getEvento().getId()).findFirst().orElse(null);
		produto2.setEvento(evento);
		

		for (Produtos produ : bancoProduto.listarProdutos()) {
			
			if(produ.getEvento().getId() == produto.getEvento().getId()) {
				
				list2.add(produ);
				
			}
			
		}
		
		mv.addObject("produto1", produto);
		mv.addObject("produtos",list2);
		
		return mv;
	}
	

}

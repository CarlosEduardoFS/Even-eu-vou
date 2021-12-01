package com.even.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Convidado;
import com.even.model.Evento;
import com.even.model.Produtos;
import com.even.service.ServicoConta;
import com.even.service.ServicoConvidado;
import com.even.service.ServicoEvento;
import com.even.service.ServicoProdutos;
import com.even.utilizatiros.Texto;

@Controller
public class ControllerCentral {
	
	Conta contaLogada;
	
	@Autowired
	ServicoConta bancoConta;
	
	@Autowired
	ServicoProdutos bancoProduto;
	
	@Autowired
	ServicoEvento bancoEvento;
	
	@Autowired
	ServicoConvidado bancoConvidado;
	
	@Autowired
	ControllerEvento controlEvento;
	
	@Autowired
	ControllerProduto controlProduto;
	
	
	@GetMapping("/pesquisarEvento")
	public ModelAndView pesuisaConta () {
		ModelAndView mv = new ModelAndView("usuario/evento/pesquisarEvento");
		
		Texto chave = new Texto();
		
		 mv.addObject("chave", chave);
		
		return mv;
	}
	
	@GetMapping("/sair")
	public String sair() {
		
		contaLogada = null;
		
		return "redirect:/index.html";
	}
	
	@GetMapping("/telaUsuario/{id}")
	public String telaUsuario(@PathVariable("id") Integer id) {
		
		Conta conta = new Conta();
		List<Conta> list = bancoConta.listarConta();
		conta = list.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		
		contaLogada = new Conta();
		contaLogada = conta;
		
		if (contaLogada != null) {
			
			return "usuario/telaUsuario";
		}else {
			return "util/paginaErro";
		}
	}
	
	
	@GetMapping("/telaUso")
	public String telaUsuario() {
		
		if (contaLogada != null) {
			
			return "usuario/telaUsuario";
			
		}else {
			
			return "util/paginaErro";
		}
	}
	
	@GetMapping("/cadastroEvento")
	public ModelAndView criarEvento() {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			mv = controlEvento.criarEvento(contaLogada);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}
	
	@PostMapping("/cadastroEvento")
	public ModelAndView salvarEvento(@ModelAttribute Evento evento) {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			List<Conta> list = bancoConta.listarConta();
			mv = controlEvento.salvarEvento(evento, contaLogada, list);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}
	
	@GetMapping("/listaEventos")
	public ModelAndView listarEventos() {
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			mv = controlEvento.listarEventos(contaLogada);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
	}
	
	@GetMapping("/paginaEvento/editarEvento/{id}")
	public ModelAndView editarEvento(@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			
			List<Evento> eve = bancoEvento.listarEvento();
			Evento evento = eve.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
			
			if (contaLogada.getId() == evento.getOrganizador().getId()) {
				excluiConvidadosEProdutos (id);
			}
			
			mv = controlEvento.editarEvento(id, contaLogada);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}	
	  
	}
	
	private void excluiConvidadosEProdutos (Integer id) {
		
		for (Produtos produto :  bancoProduto.listarProdutos()) {
			
			if (produto.getEvento().getId() == id) {
				
				produto.setAtivo(false);
				
				bancoProduto.saveProdutos(produto);
				
			}
		}
		for (Convidado convidado : bancoConvidado.listarConvidado()) {
			
			if (convidado.getEvento().getId() == id) {
				
				convidado.setAtivo(false);
				bancoConvidado.saveConvidado(convidado);
			}
		}
		
	}
	
	@GetMapping("/paginaEvento/removerEvento/{id}")
	public String removerEvento(@PathVariable("id") Integer id) {
		
			String mv;
		
		if (contaLogada != null) {
			mv = controlEvento.removerEvento(id, contaLogada);
			return mv;
		}else {
			
			return "util/paginaErro";
		}		
		
	}
	
	@GetMapping("/paginaEvento/{id}")
	public ModelAndView paginaEvento (@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView();
		
		List<Produtos> produtos = controlEvento.litaProdutosEvento(id);
		
		if (contaLogada != null) {
			mv = controlEvento.paginaEvento(id, contaLogada, produtos);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
	}
	
	@GetMapping("/cadastroProduto/{id}")
	public ModelAndView cadastroProduto(@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			List<Evento> list = bancoEvento.listarEvento();
			Evento evento = list.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
			mv = controlProduto.cadastroProduto(id, evento);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvarProduto(@ModelAttribute Produtos produto) {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			mv = controlProduto.salvarProduto(produto);
			return mv;
		}else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}

}

package com.even.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Convidado;
import com.even.model.Evento;
import com.even.model.InformacoesTecnicasEvento;
import com.even.model.Produtos;
import com.even.service.ServicoConta;
import com.even.service.ServicoConvidado;
import com.even.service.ServicoEvento;
import com.even.service.ServicoProdutos;
import com.even.utilizatiros.Texto;

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
	
	@Autowired
	ServicoConvidado bancoConvidados;
	
	
	@GetMapping("/pesquisarEvento")
	public ModelAndView pesuisaConta () {
		ModelAndView mv = new ModelAndView("util/pesquisarEvento");
		
		Texto chave = new Texto();
		
		 mv.addObject("chave", chave);
		
		return mv;
	}

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
		
		if (contaLogada != null) {
			
			return "telaUsuario";
		}else {
			return "util/paginaErro";
		}
		
		
	}
	
	@GetMapping("/telaUso")
	public String telaUsuario() {
		
		if (contaLogada != null) {
			
			return "telaUsuario";
			
		}else {
			
			return "util/paginaErro";
		}
	}
	
	
	@GetMapping("/cadastroEvento")
	public ModelAndView criarEvento() {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			
			mv.setViewName("cadastroEvento");
			Evento evento = new Evento();
			
			evento.setOrganizador(contaLogada);
			mv.addObject("evento", evento);
			
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
			
			Random random = new Random();
			StringBuilder sb = new StringBuilder();
			
			mv.setViewName("redirecionamentos/redireInformacoes");
			
			InformacoesTecnicasEvento informacoes = evento.getInformacoes();
			
			informacoes.setQuantidadeCadeiras();
			informacoes.setQuantidadeMesas();
			evento.setInformacoes(informacoes);
			evento.setAtivo(true);
			
			List<Conta> list = banco.listarConta();
			int idConta = evento.getOrganizador().getId();
			Conta conta = list.stream().filter(x -> x.getId() == idConta).findFirst().orElse(null);
			
			for (int i = 0; i < 4; i++) {
				
				char randomizedCharacter = (char) (random.nextInt(26) + 'a');
				sb.append(randomizedCharacter);	
				
			}
			
			evento.setChaveBusca(sb.toString());
			
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
			
			if (evento.getConvidadosLevamProdutos() || evento.getPrecisaDeProdutos()) {
			
				Evento evento2 = new Evento();
				evento2.setId(evento.getId());
				
				mv.addObject("evento", evento2);
				
				return mv;
			}
			
			mv.setViewName("telaUsuario");
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
			
			mv.setViewName("cadastroProduto");
		
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
		}else {
			
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvarProduto(@ModelAttribute Produtos produto) {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			
			mv.setViewName("cadastroProduto");
		
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
			
		}else {
			
			mv.setViewName("util/paginaErro");
			return mv;
			
		}
	}
	
	@GetMapping("/listaEventos")
	public ModelAndView listarEventos() {
		
		ModelAndView mv = new ModelAndView();
		
		if (contaLogada != null) {
			
			mv.setViewName("listarEventos");
			
			List<Evento> lista = bancoEvento.listarEvento();
			List<Evento> lista2 = new LinkedList<>();
				
			for (Evento eve : lista) {
					
				if (contaLogada.getId() == eve.getOrganizador().getId()) {
						
					if (eve.getAtivo())
						lista2.add(eve);
				}
			}
				
			mv.addObject("eventos", lista2);
			return mv;
			
		}else {
			
			mv.setViewName("util/paginaErro");
			return mv;
		}
		
	}
	
	public List<Produtos> litaProdutosEvento (Integer id){
		
		List<Produtos> lista2 = new LinkedList<>();
		
		for (Produtos produ : bancoProduto.listarProdutos()) {
			
			if(produ.getEvento().getId() == id) {
				
				lista2.add(produ);
			}
		}
		
		return lista2;
	}
	
	@GetMapping("/paginaEvento/{id}")
	public ModelAndView paginaEvento (@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView("paginaEvento");
		
		if ((contaLogada != null)) {
			
		
			List<Evento> lista = bancoEvento.listarEvento();
			
			Evento evento = lista.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
			
			if (contaLogada.getId() == evento.getOrganizador().getId()) {
				
				List<Produtos> listProdutos = litaProdutosEvento(evento.getId());
				List<Convidado> listConvidados = litaConvidadoEvento(evento.getId());
				
				InformacoesTecnicasEvento informacoes = evento.getInformacoes();
				
				informacoes.setProdutos(listProdutos);
				
				evento.setInformacoes(informacoes);
				evento.setConvidados(listConvidados);
				
				mv.addObject("evento", evento);
			
				return mv;
			}else {
				
				mv.setViewName("util/paginaErro");
				return mv;
				
			}
			
		}else {
			
			mv.setViewName("util/paginaErro");
			return mv;
			
		}
		
	}
	
	@GetMapping("/paginaEvento/editarEvento/{id}")
	public ModelAndView editarEvento(@PathVariable("id") Integer id) {
		
		ModelAndView mv = new ModelAndView();
		
		if ((contaLogada != null)) {
			
			List<Evento> eve = bancoEvento.listarEvento();
			Evento evento = eve.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
			
			if (contaLogada.getId() == evento.getOrganizador().getId()) {
				
				mv.setViewName("editarEvento");
				mv.addObject("evento", evento);
				return mv;
				
			}else {
				
				mv.setViewName("util/paginaErro");
				return mv;
				
			}
		}else {
			
			mv.setViewName("util/paginaErro");
			return mv;
			
		}
	}
	
	@GetMapping("/paginaEvento/removerEvento/{id}")
	public String removerEvento(@PathVariable("id") Integer id) {
		
		if ((contaLogada != null)) {
		
			List<Evento> even = bancoEvento.listarEvento();
			Evento evento = even.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
			
			if (contaLogada.getId() == evento.getOrganizador().getId()) {
			
				evento.setAtivo(false);
				bancoEvento.saveEvento(evento);
				return "redirect:/listaEventos";
			}else {
				
				return "util/paginaErro";
				
			}
		}else {
			
			return "util/paginaErro";
			
		}
	}
	
	@PostMapping("/buscarEventoConvidade")
	public ModelAndView buscarEvento  (@ModelAttribute Texto chave) {
		
		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();
		
		System.out.println("Entrou 2"+chave.getTexto());
		
		Evento evento = eventos.stream().filter(x -> x.getChaveBusca().equals(chave.getTexto())).findFirst().orElse(null);
		
		if (evento != null) {
			
			mv.setViewName("editarEvento");
			
			List<Produtos> produtos = litaProdutosEvento(evento.getId());
			Convidado convidado = new Convidado();
			
			convidado.setEvento(evento);
			
			mv.setViewName("presenca");
			mv.addObject("produtos", produtos);
			mv.addObject("convidado", convidado);
			
			return mv;
			
		}
		
		mv.setViewName("util/paginaErro");
		return mv;
		
	}
	
	public List<Convidado> litaConvidadoEvento (Integer id){
		
		List<Convidado> lista2 = new LinkedList<>();
		
		for (Convidado convidado : bancoConvidados.listarConvidado()) {
			
			if(convidado.getEvento().getId() == id) {
				
				lista2.add(convidado);
			}
		}
		
		return lista2;
	}
	
	@PostMapping("/salvarConvidado")
	public ModelAndView cadastrarConvidade(@ModelAttribute Convidado convidado) {
		
		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();
		Evento evento = eventos.stream().filter(x -> x.getId() == convidado.getEvento().getId()).findFirst().orElse(null);
		
		List<Produtos> produtos = bancoProduto.listarProdutos();
		Produtos produto = produtos.stream().filter(x -> x.getId() == convidado.getProduto().getId()).findFirst().orElse(null);
		produto.setQuantidadeConfirmada(convidado.getProduto().getQuantidade());
		bancoProduto.saveProdutos(produto);
		
		List<Convidado> listConvidado = litaConvidadoEvento(evento.getId());
		listConvidado.add(convidado);
		evento.setConvidados(listConvidado);
		
		bancoConvidados.saveConvidado(convidado);
		bancoEvento.saveEvento(evento);
		
		
		mv.setViewName("util/confirmacaoPresenca");
		
		return mv;
		
	}
	
	
}

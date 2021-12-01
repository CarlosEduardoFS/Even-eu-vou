package com.even.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Convidado;
import com.even.model.Evento;
import com.even.model.Produtos;
import com.even.service.ServicoConvidado;
import com.even.service.ServicoEvento;
import com.even.service.ServicoProdutos;
import com.even.utilizatiros.Texto;

@Controller
public class ControllerConvidado {
	
	@Autowired
	ServicoConvidado bancoConvidados;
	
	@Autowired
	ServicoProdutos bancoProduto;
	
	@Autowired
	ServicoEvento bancoEvento;
	
	public List<Produtos> litaProdutosEventoAtivos (Integer id){
		
		List<Produtos> lista2 = new LinkedList<>();
		
		for (Produtos produ : bancoProduto.listarProdutos()) {
			
			if((produ.getEvento().getId() == id) && (produ.getQuantidade() != null) && (produ.getQuantidadeConfirmada() != null)) {
				if (produ.getAtivo()) {
					if (produ.getQuantidade() > produ.getQuantidadeConfirmada())
						lista2.add(produ);
					
				}		
			}
		}
		
		return lista2;
	}
	
	@PostMapping("/buscarEventoConvidado")
	public ModelAndView buscarEvento  (@ModelAttribute Texto chave) {
		
		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();
		
		Evento evento = eventos.stream().filter(x -> x.getChaveBusca().equals(chave.getTexto())).findFirst().orElse(null);
		
		if (evento != null) {
			
			List<Produtos> produtos = litaProdutosEventoAtivos(evento.getId());
			Convidado convidado = new Convidado();
			
			convidado.setEvento(evento);
			
			mv.addObject("produtos", produtos);
			mv.addObject("convidado", convidado);
			
			if (evento.getConvidadosLevamProdutos()) {
				
				mv.setViewName("usuario/presenca");
				return mv;
			}else {
				mv.setViewName("usuario/presencaSemProduto");
				return mv;
				
			}
			
			
		} 
		
		mv.setViewName("util/paginaErro");
		return mv;
		
	}
	
	public List<Convidado> litaConvidadoEvento (Integer id){
		
		List<Convidado> lista2 = new LinkedList<>();
		
		for (Convidado convidado : bancoConvidados.listarConvidado()) {
			
			if (convidado.getAtivo()) {
				if((convidado.getEvento() != null) && (convidado.getEvento().getId() == id)  ) {
					
					lista2.add(convidado);
				}
			}	
		}
		
		return lista2;
	}
	
	@PostMapping("/salvarConvidado")
	public ModelAndView cadastrarConvidade(@ModelAttribute Convidado convidado) {
		
		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();
		Evento evento = eventos.stream().filter(x -> x.getId() == convidado.getEvento().getId()).findFirst().orElse(null);
		
		if (convidado.getProduto() != null) {
			List<Produtos> produtos = bancoProduto.listarProdutos();
			Produtos produto = produtos.stream().filter(x -> x.getId() == convidado.getProduto().getId()).findFirst().orElse(null);
			produto.setQuantidadeConfirmada(convidado.getProduto().getQuantidade());
			bancoProduto.saveProdutos(produto);
			
		}
		
		List<Convidado> listConvidado = litaConvidadoEvento(evento.getId());
		listConvidado.add(convidado);
		evento.setConvidados(listConvidado);
		
		convidado.setAtivo(true);
		bancoConvidados.saveConvidado(convidado);
		bancoEvento.saveEvento(evento);
		
		
		mv.setViewName("util/confirmacaoPresenca");
		
		return mv;
		
	}
	

}

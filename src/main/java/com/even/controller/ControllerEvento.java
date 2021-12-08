package com.even.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Convidado;
import com.even.model.Evento;
import com.even.model.InformacoesTecnicasEvento;
import com.even.model.Produtos;
import com.even.service.ServicoConta;
import com.even.service.ServicoEvento;

@Controller
public class ControllerEvento {

	@Autowired
	ServicoEvento bancoEvento;

	public ModelAndView criarEvento(Conta organizador) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/evento/cadastroEvento");
		Evento evento = new Evento();

		evento.setOrganizador(organizador);
		mv.addObject("evento", evento);

		return mv;

	}

	public ModelAndView salvarEvento(Evento evento, Conta contaLogada, List<Conta> list, ServicoConta bancoConta) {

		ModelAndView mv = new ModelAndView(); 
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		mv.setViewName("redirecionamentos/redireInformacoes");
		InformacoesTecnicasEvento informacoes = evento.getInformacoes();
		
		informacoes.setQuantidadeCadeiras();
		informacoes.setQuantidadeMesas();
		evento.setInformacoes(informacoes);
		evento.setAtivo(true);

		Conta conta = contaLogada;

		for (int i = 0; i < 4; i++) {

			char randomizedCharacter = (char) (random.nextInt(26) + 'a');
			sb.append(randomizedCharacter);

		}

		evento.setChaveBusca(sb.toString());
		bancoEvento.saveEvento(evento);

		List<Evento> listEve = new LinkedList<>();
		for (Evento evento2 : bancoEvento.listarEvento()) {

			if (evento2.getOrganizador().getId() == contaLogada.getId()) {
				listEve.add(evento);
			}
		}

		listEve.add(evento);
		conta.setEventos(listEve);
		bancoConta.saveConta(conta);

		if (evento.getConvidadosLevamProdutos() || evento.getPrecisaDeProdutos()) {

			Evento evento2 = new Evento();
			evento2.setId(evento.getId());
			mv.addObject("evento", evento2);
			return mv;
		}

		mv.setViewName("usuario/telaUsuario");
		return mv;

	}

	public ModelAndView listarEventos(Conta contaLogada) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/evento/listarEventos");

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

	}

	public ModelAndView editarEvento(Integer id, Conta contaLogada) {

		ModelAndView mv = new ModelAndView();

		List<Evento> eve = bancoEvento.listarEvento();
		Evento evento = eve.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizador().getId()) {
			
			
			mv.setViewName("usuario/evento/editarEvento");
			mv.addObject("evento", evento);
			return mv;

		} else {

			mv.setViewName("util/paginaErro");
			return mv;

		}
	}

	public String removerEvento(Integer id, Conta contaLogada) {

		List<Evento> even = bancoEvento.listarEvento();
		Evento evento = even.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizador().getId()) {

			evento.setAtivo(false);
			bancoEvento.saveEvento(evento);
			return "redirect:/listaEventos";
		} else {

			return "util/paginaErro";

		}

	}

	public List<Produtos> litaProdutosEvento(Integer id, List<Produtos> produtos) {

		List<Produtos> lista2 = new LinkedList<>();
		
		for (Produtos produ : produtos) {
			
			if (produ.getAtivo()) {
				
				if (produ.getEvento().getId() == id) {

					lista2.add(produ);
				}
			}
		}

		return lista2;
	}

	public List<Convidado> litaConvidadoEvento(Integer id,List<Convidado> convidados) {

		List<Convidado> lista2 = new LinkedList<>();

		for (Convidado convidado : convidados) {
			if (convidado.getAtivo()) {
				if (convidado.getEvento().getId() == id) {

					lista2.add(convidado);
				}
			}
		}

		return lista2;
	}

	public ModelAndView paginaEvento(Integer id, Conta contaLogada, List<Produtos> listProdutos, List<Convidado> convidados) {

		ModelAndView mv = new ModelAndView();

		List<Evento> lista = bancoEvento.listarEvento();

		Evento evento = lista.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizador().getId()) {

			List<Convidado> listConvidados = litaConvidadoEvento(evento.getId(), convidados);

			InformacoesTecnicasEvento informacoes = evento.getInformacoes();

			informacoes.setProdutos(listProdutos);

			evento.setInformacoes(informacoes);
			evento.setConvidados(listConvidados);
			mv.addObject("evento", evento);
			if (!evento.getConvidadosLevamProdutos() && !evento.getPrecisaDeProdutos()) {
				
				mv.setViewName("usuario/evento/paginaEventoSemProduto");
				return mv;
				
			} else if (!evento.getConvidadosLevamProdutos()) {
				
				mv.setViewName("usuario/evento/paginaEventoSemProdutosUsuario");
				return mv;
				
			}
			
			mv.setViewName("usuario/evento/paginaEvento");
			return mv;
			
		} else {

			mv.setViewName("util/paginaErro");
			return mv;

		}

	}

}

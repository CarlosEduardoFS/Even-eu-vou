package com.even.resources;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.domain.Account;
import com.even.model.domain.Guest;
import com.even.model.domain.Event;
import com.even.model.domain.TechnicalInformationEvent;
import com.even.model.domain.Product;
import com.even.model.service.ServicoConta;
import com.even.model.service.ServicoEvento;

@Controller
public class ControllerEvento {

	@Autowired
	ServicoEvento bancoEvento;

	public ModelAndView criarEvento(Account organizador) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/evento/cadastroEvento");
		Event evento = new Event();

		evento.setOrganizer(organizador);
		mv.addObject("evento", evento);

		return mv;

	}

	public ModelAndView salvarEvento(Event evento, Account contaLogada, List<Account> list, ServicoConta bancoConta) {

		ModelAndView mv = new ModelAndView(); 
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		mv.setViewName("redirecionamentos/redireInformacoes");
		TechnicalInformationEvent informacoes = evento.getInformation();
		
		informacoes.setQuantidadeCadeiras();
		informacoes.setQuantidadeMesas();
		evento.setInformation(informacoes);
		evento.setActive(true);

		Account conta = contaLogada;

		for (int i = 0; i < 4; i++) {

			char randomizedCharacter = (char) (random.nextInt(26) + 'a');
			sb.append(randomizedCharacter);

		}

		evento.setKeySearch(sb.toString());
		bancoEvento.saveEvento(evento);

		List<Event> listEve = new LinkedList<>();
		for (Event evento2 : bancoEvento.listarEvento()) {

			if (evento2.getOrganizer().getId() == contaLogada.getId()) {
				listEve.add(evento);
			}
		}

		listEve.add(evento);
		conta.setEventos(listEve);
		bancoConta.saveConta(conta);

		if (evento.getGuestsTakeProducts() || evento.getNeedProducts()) {

			Event evento2 = new Event();
			evento2.setId(evento.getId());
			mv.addObject("evento", evento2);
			return mv;
		}

		mv.setViewName("usuario/telaUsuario");
		return mv;

	}

	public ModelAndView listarEventos(Account contaLogada) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/evento/listarEventos");

		List<Event> lista = bancoEvento.listarEvento();
		List<Event> lista2 = new LinkedList<>();

		for (Event eve : lista) {

			if (contaLogada.getId() == eve.getOrganizer().getId()) {

				if (eve.getActive())
					lista2.add(eve);
			}
		}

		mv.addObject("eventos", lista2);
		return mv;

	}

	public ModelAndView editarEvento(Integer id, Account contaLogada) {

		ModelAndView mv = new ModelAndView();

		List<Event> eve = bancoEvento.listarEvento();
		Event evento = eve.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizer().getId()) {
			
			
			mv.setViewName("usuario/evento/editarEvento");
			mv.addObject("evento", evento);
			return mv;

		} else {

			mv.setViewName("util/paginaErro");
			return mv;

		}
	}

	public String removerEvento(Integer id, Account contaLogada) {

		List<Event> even = bancoEvento.listarEvento();
		Event evento = even.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizer().getId()) {

			evento.setActive(false);
			bancoEvento.saveEvento(evento);
			return "redirect:/listaEventos";
		} else {

			return "util/paginaErro";

		}

	}

	public List<Product> litaProdutosEvento(Integer id, List<Product> produtos) {

		List<Product> lista2 = new LinkedList<>();
		
		for (Product produ : produtos) {
			
			if (produ.getAtivo()) {
				
				if (produ.getEvento().getId() == id) {

					lista2.add(produ);
				}
			}
		}

		return lista2;
	}

	public List<Guest> litaConvidadoEvento(Integer id,List<Guest> convidados) {

		List<Guest> lista2 = new LinkedList<>();

		for (Guest convidado : convidados) {
			if (convidado.getAtivo()) {
				if (convidado.getEvento().getId() == id) {

					lista2.add(convidado);
				}
			}
		}

		return lista2;
	}

	public ModelAndView paginaEvento(Integer id, Account contaLogada, List<Product> listProdutos, List<Guest> convidados) {

		ModelAndView mv = new ModelAndView();

		List<Event> lista = bancoEvento.listarEvento();

		Event evento = lista.stream().filter(x -> x.getId() == id).findFirst().orElse(null);

		if (contaLogada.getId() == evento.getOrganizer().getId()) {

			List<Guest> listConvidados = litaConvidadoEvento(evento.getId(), convidados);

			TechnicalInformationEvent informacoes = evento.getInformation();

			informacoes.setProdutos(listProdutos);

			evento.setInformation(informacoes);
			evento.setGuests(listConvidados);
			mv.addObject("evento", evento);
			if (!evento.getGuestsTakeProducts() && !evento.getNeedProducts()) {
				
				mv.setViewName("usuario/evento/paginaEventoSemProduto");
				return mv;
				
			} else if (!evento.getGuestsTakeProducts()) {
				
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

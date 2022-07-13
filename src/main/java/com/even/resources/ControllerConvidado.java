package com.even.resources;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.domain.Guest;
import com.even.model.domain.Event;
import com.even.model.domain.Product;
import com.even.model.service.ServicoConvidado;
import com.even.model.service.ServicoEvento;
import com.even.model.service.ServicoProdutos;
import com.even.resources.utilities.Text;

@Controller
public class ControllerConvidado {

	@Autowired
	ServicoConvidado bancoConvidados;

	public List<Product> litaProdutosEventoAtivos(Integer id, List<Product> produtos) {

		List<Product> lista2 = new LinkedList<>();

		for (Product produ : produtos) {

			if ((produ.getEvento().getId() == id) && (produ.getQuantidade() != null)
					&& (produ.getQuantidadeConfirmada() != null)) {
				if (produ.getAtivo()) {
					if (produ.getQuantidade() > produ.getQuantidadeConfirmada())
						produ.setQuantidade(produ.getQuantidade() - produ.getQuantidadeConfirmada());
						lista2.add(produ);
				}
			}
		}

		return lista2;
	}

	public ModelAndView buscarEvento(Text chave, List<Product> listProduto, ServicoEvento bancoEvento) {

		ModelAndView mv = new ModelAndView();
		List<Event> eventos = bancoEvento.listarEvento();

		Event evento = eventos.stream().filter(x -> x.getKeySearch().equals(chave.getText())).findFirst()
				.orElse(null);

		if (evento != null) {

			List<Product> produtos = litaProdutosEventoAtivos(evento.getId(), listProduto);
			Guest convidado = new Guest();

			convidado.setEvento(evento);

			mv.addObject("produtos", produtos);
			mv.addObject("convidado", convidado);

			if (evento.getGuestsTakeProducts()) {

				mv.setViewName("usuario/presenca");
				return mv;
			} else {
				mv.setViewName("usuario/presencaSemProduto");
				return mv;

			}
		}

		mv.setViewName("util/paginaErro");
		return mv;

	}

	public List<Guest> litaConvidadoEvento(Integer id) {

		List<Guest> lista2 = new LinkedList<>();

		for (Guest convidado : bancoConvidados.listarConvidado()) {

			if (convidado.getAtivo()) {
				if ((convidado.getEvento() != null) && (convidado.getEvento().getId() == id)) {

					lista2.add(convidado);
				}
			}
		}

		return lista2;
	}

	public ModelAndView cadastrarConvidade(Guest convidado, ServicoProdutos bancoProduto,
			ServicoEvento bancoEvento) {

		ModelAndView mv = new ModelAndView();
		List<Event> eventos = bancoEvento.listarEvento();
		Event evento = eventos.stream().filter(x -> x.getId() == convidado.getEvento().getId()).findFirst()
				.orElse(null);

		if (convidado.getProduto() != null) {
			List<Product> produtos = bancoProduto.listarProdutos();
			Product produto = produtos.stream().filter(x -> x.getId() == convidado.getProduto().getId()).findFirst()
					.orElse(null);
			produto.setQuantidadeConfirmada(convidado.getQuantidade());
			bancoProduto.saveProdutos(produto);


		}

		List<Guest> listConvidado = litaConvidadoEvento(evento.getId());
		listConvidado.add(convidado);
		evento.setGuests(listConvidado);

		convidado.setAtivo(true);
		bancoConvidados.saveConvidado(convidado);
		bancoEvento.saveEvento(evento);

		mv.setViewName("util/confirmacaoPresenca");

		return mv;

	}

}

package com.even.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	public List<Produtos> litaProdutosEventoAtivos(Integer id, List<Produtos> produtos) {

		List<Produtos> lista2 = new LinkedList<>();

		for (Produtos produ : produtos) {

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

	public ModelAndView buscarEvento(Texto chave, List<Produtos> listProduto, ServicoEvento bancoEvento) {

		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();

		Evento evento = eventos.stream().filter(x -> x.getChaveBusca().equals(chave.getTexto())).findFirst()
				.orElse(null);

		if (evento != null) {

			List<Produtos> produtos = litaProdutosEventoAtivos(evento.getId(), listProduto);
			Convidado convidado = new Convidado();

			convidado.setEvento(evento);

			mv.addObject("produtos", produtos);
			mv.addObject("convidado", convidado);

			if (evento.getConvidadosLevamProdutos()) {

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

	public List<Convidado> litaConvidadoEvento(Integer id) {

		List<Convidado> lista2 = new LinkedList<>();

		for (Convidado convidado : bancoConvidados.listarConvidado()) {

			if (convidado.getAtivo()) {
				if ((convidado.getEvento() != null) && (convidado.getEvento().getId() == id)) {

					lista2.add(convidado);
				}
			}
		}

		return lista2;
	}

	public ModelAndView cadastrarConvidade(Convidado convidado, ServicoProdutos bancoProduto,
			ServicoEvento bancoEvento) {

		ModelAndView mv = new ModelAndView();
		List<Evento> eventos = bancoEvento.listarEvento();
		Evento evento = eventos.stream().filter(x -> x.getId() == convidado.getEvento().getId()).findFirst()
				.orElse(null);

		if (convidado.getProduto() != null) {
			List<Produtos> produtos = bancoProduto.listarProdutos();
			Produtos produto = produtos.stream().filter(x -> x.getId() == convidado.getProduto().getId()).findFirst()
					.orElse(null);
			produto.setQuantidadeConfirmada(convidado.getQuantidade());
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

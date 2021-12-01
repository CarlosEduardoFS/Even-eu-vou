package com.even.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Evento;
import com.even.model.Produtos;
import com.even.service.ServicoEvento;
import com.even.service.ServicoProdutos;

@Controller
public class ControllerProduto {

	@Autowired
	ServicoProdutos bancoProduto;

	@Autowired
	ServicoEvento bancoEvento;

	public ModelAndView cadastroProduto(Integer id, Evento evento) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/produto/cadastroProduto");

		List<Produtos> list2 = new LinkedList<>();

		Produtos produto = new Produtos();

		produto.setEvento(evento);

		for (Produtos produ : bancoProduto.listarProdutos()) {
			if (produ.getAtivo()) {
				
				if (produ.getEvento().getId() == produto.getEvento().getId()) {

					list2.add(produ);

				}
			}
		}

		mv.addObject("produto1", produto);
		mv.addObject("produtos", list2);

		return mv;

	}

	public ModelAndView salvarProduto(Produtos produto) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/produto/cadastroProduto");
		
		produto.setAtivo(true);

		List<Evento> list = bancoEvento.listarEvento();
		List<Produtos> produtos = new LinkedList<>();
		List<Produtos> list2 = new LinkedList<>();

		for (Produtos produtos2 : bancoProduto.listarProdutos()) {

			if ((produto.getEvento().getId() == produtos2.getEvento().getId()) && (produtos2 != null)) {
				produtos.add(produtos2);
			}

		}

		produto.setQuantidadeConfirmada(0);
		produtos.add(produto);

		bancoProduto.saveProdutos(produto);

		Produtos produto2 = new Produtos();
		Evento evento = list.stream().filter(x -> x.getId() == produto.getEvento().getId()).findFirst().orElse(null);
		produto2.setEvento(evento);

		for (Produtos produ : bancoProduto.listarProdutos()) {
			if (produ.getAtivo()){
				
				if (produ.getEvento().getId() == produto.getEvento().getId()) {

					list2.add(produ);

				}
			}
		}

		mv.addObject("produto1", produto);
		mv.addObject("produtos", list2);

		return mv;

	}

}

package com.even.resources;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.domain.Event;
import com.even.model.domain.Product;
import com.even.model.service.ServicoProdutos;

@Controller
public class ControllerProduto {

	@Autowired
	ServicoProdutos bancoProduto;

	public ModelAndView cadastroProduto(Integer id, Event evento) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/produto/cadastroProduto");

		List<Product> list2 = new LinkedList<>();

		Product produto = new Product();

		produto.setEvento(evento);

		for (Product produ : bancoProduto.listarProdutos()) {
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

	public ModelAndView salvarProduto(Product produto, List<Event> list) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("usuario/produto/cadastroProduto");
		produto.setAtivo(true);

		List<Product> produtos = new LinkedList<>();
		List<Product> list2 = new LinkedList<>();

		for (Product produtos2 : bancoProduto.listarProdutos()) {

			if ((produto.getEvento().getId() == produtos2.getEvento().getId()) && (produtos2 != null)) {
				produtos.add(produtos2);
			}

		}

		produto.setQuantidadeConfirmada(0);
		produtos.add(produto);

		bancoProduto.saveProdutos(produto);

		Product produto2 = new Product();
		Event evento = list.stream().filter(x -> x.getId() == produto.getEvento().getId()).findFirst().orElse(null);
		produto2.setEvento(evento);

		for (Product produ : bancoProduto.listarProdutos()) {
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

}

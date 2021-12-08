package com.even.controller;

import java.util.Collections;
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
import com.even.model.Login;
import com.even.model.Produtos;
import com.even.service.ServicoConta;
import com.even.service.ServicoConvidado;
import com.even.service.ServicoEvento;
import com.even.service.ServicoLogin;
import com.even.service.ServicoProdutos;
import com.even.utilizatiros.Texto;

@Controller
public class ControllerCentral {

	Conta contaLogada;

	@Autowired
	ServicoConta bancoConta;

	@Autowired
	ServicoLogin bancoLogin;

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

	@Autowired
	ControllerConta controlConta;

	@Autowired
	ControllerConvidado controlConvidado;

	@Autowired
	ControllerLogin controlLogin;

	@GetMapping("/pesquisarEvento")
	public ModelAndView pesuisaConta() {
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

	@GetMapping("/cadastro")
	public ModelAndView telaCaddastroConta() {

		return controlConta.cadastro();

	}

	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute Conta conta) {

		List<Login> logins = bancoLogin.listarLogin();
		Collections.sort(logins);
		int posicao = Collections.binarySearch(logins, conta.getLogin(), null);

		if (posicao >= 0) {

			return "util/contaExiste";
		}

		return controlConta.salvarConta(conta);

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
		} else {
			return "util/paginaErro";
		}
	}

	@GetMapping("/telaUso")
	public String telaUsuario() {

		if (contaLogada != null) {

			return "usuario/telaUsuario";

		} else {

			return "util/paginaErro";
		}
	}

	@GetMapping("/cadastroEvento")
	public ModelAndView criarEvento() {

		ModelAndView mv = new ModelAndView();

		if (contaLogada != null) {
			mv = controlEvento.criarEvento(contaLogada);
			return mv;
		} else {
			mv.setViewName("util/paginaErro");
			return mv;
		}

	}

	@PostMapping("/cadastroEvento")
	public ModelAndView salvarEvento(@ModelAttribute Evento evento) {

		ModelAndView mv = new ModelAndView();

		if (contaLogada != null) {
			List<Conta> list = bancoConta.listarConta();
			mv = controlEvento.salvarEvento(evento, contaLogada, list, bancoConta);
			return mv;
		} else {
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
		} else {
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
				excluiConvidadosEProdutos(id);
			}

			mv = controlEvento.editarEvento(id, contaLogada);
			return mv;
		} else {
			mv.setViewName("util/paginaErro");
			return mv;
		}

	}

	private void excluiConvidadosEProdutos(Integer id) {

		for (Produtos produto : bancoProduto.listarProdutos()) {

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
		} else {

			return "util/paginaErro";
		}

	}

	@GetMapping("/paginaEvento/{id}")
	public ModelAndView paginaEvento(@PathVariable("id") Integer id) {

		ModelAndView mv = new ModelAndView();

		List<Produtos> produtos = controlEvento.litaProdutosEvento(id, bancoProduto.listarProdutos());

		if (contaLogada != null) {
			mv = controlEvento.paginaEvento(id, contaLogada, produtos, bancoConvidado.listarConvidado());
			return mv;
		} else {
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
		} else {
			mv.setViewName("util/paginaErro");
			return mv;
		}

	}

	@PostMapping("/salvarProduto")
	public ModelAndView salvarProduto(@ModelAttribute Produtos produto) {

		ModelAndView mv = new ModelAndView();

		if (contaLogada != null) {
			mv = controlProduto.salvarProduto(produto, bancoEvento.listarEvento());
			return mv;
		} else {
			mv.setViewName("util/paginaErro");
			return mv;
		}
	}

	@PostMapping("/buscarEventoConvidado")
	public ModelAndView buscarEvento(@ModelAttribute Texto chave) {

		return controlConvidado.buscarEvento(chave, bancoProduto.listarProdutos(), bancoEvento);

	}

	@PostMapping("/salvarConvidado")
	public ModelAndView cadastrarConvidade(@ModelAttribute Convidado convidado) {

		return controlConvidado.cadastrarConvidade(convidado, bancoProduto, bancoEvento);
	}

	@PostMapping("/salvarLogin")
	public String salvarLogin(@ModelAttribute Login login) {

		return controlLogin.salvarLogin(login);

	}

	@GetMapping("/login")
	public ModelAndView Login() {

		return controlLogin.Login();
	}

	@PostMapping("/verificaConta")
	public ModelAndView verificaConta(@ModelAttribute Login login) {

		return controlLogin.verificaConta(login, bancoConta);
	}

	@GetMapping("/atualizarSenha")
	public ModelAndView atualizarSenha() {
		return controlLogin.atualizarSenha();

	}

	@PostMapping("/verificaEmail")
	public ModelAndView atualizarSenha(@ModelAttribute Login login) {

		return controlLogin.atualizarSenha(login);

	}

}

package com.even.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.Conta;
import com.even.model.Login;
import com.even.service.ServicoConta;
import com.even.service.ServicoLogin;

@Controller
public class ControllerLogin {

	@Autowired
	ServicoLogin bancoLogin;

	public String salvarLogin(Login login) {

		bancoLogin.saveLogin(login);
		return "conta/login";
	}

	public ModelAndView Login() {

		ModelAndView mv = new ModelAndView("conta/login");
		Login login = new Login();
		mv.addObject("login", login);
		return mv;

	}

	public ModelAndView verificaConta(Login login, ServicoConta bancoConta) {

		List<Login> logins = bancoLogin.listarLogin();
		Collections.sort(logins);
		int posicao = Collections.binarySearch(logins, login, null);

		if (posicao >= 0) {

			Login loginBanco = logins.get(posicao);

			if (isLogin(loginBanco, login)) {
				Conta contaLog = contaLogada(loginBanco.getId(), bancoConta);

				ModelAndView mv = new ModelAndView("redirecionamentos/redireTelaUsuario");
				mv.addObject("conta", contaLog);

				return mv;

			}
		}

		ModelAndView mv = new ModelAndView("redirect:/login");
		return mv;
	}

	private Conta contaLogada(int id, ServicoConta bancoConta) {

		Conta conta = new Conta();

		for (Conta conta2 : bancoConta.listarConta()) {

			Login login2 = conta2.getLogin();

			if (login2.getId() == id) {

				conta = conta2;

			}
		}

		return conta;

	}

	private boolean isLogin(Login loginBanco, Login loginInformado) {

		if (verificaEmail(loginBanco, loginInformado) && loginBanco.getSenha().equals(loginInformado.getSenha()))
			return true;

		return false;

	}

	public ModelAndView atualizarSenha() {

		ModelAndView mv = new ModelAndView("conta/buscaEmail");
		Login login = new Login();
		mv.addObject("login", login);

		return mv;
	}

	public ModelAndView atualizarSenha(Login login) {

		List<Login> logins = bancoLogin.listarLogin();

		Collections.sort(logins);

		int posicao = Collections.binarySearch(logins, login, null);

		if (posicao >= 0) {

			Login loginBanco = logins.get(posicao);

			if (verificaEmail(loginBanco, login)) {
				ModelAndView mv = new ModelAndView("conta/altSenha");
				login.setId(loginBanco.getId());
				mv.addObject("login", login);
				return mv;
			}

		}
		ModelAndView mv = new ModelAndView("util/atualizarSenha");

		return mv;
	}

	private boolean verificaEmail(Login loginBanco, Login loginInformado) {

		if (loginBanco.getEmail().equals(loginInformado.getEmail())) {
			return true;

		}

		return false;
	}

}

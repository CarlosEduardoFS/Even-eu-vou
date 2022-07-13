package com.even.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.even.model.domain.Account;
import com.even.model.service.ServicoConta;

@Controller
public class ControllerConta {
	
	@Autowired
	ServicoConta banco;
	
	public ModelAndView cadastro() {
		
		ModelAndView mv = new ModelAndView("conta/cadastro");
		Account conta = new Account();
		mv.addObject("conta", conta);
		
		return mv;
		
	}
	
	public String salvarConta (Account conta) {
		
		banco.saveConta(conta);
		
		return "conta/login";
		
	}	
}

package br.com.meudinheiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PainelController {

	@GetMapping("/admin/painel")
	public String painelAdmin() {
		return "admin";
	}

	@GetMapping("/usuario/painel")
	public String painelUsuario() {
		return "usuario";
	}

	@GetMapping("/visitante/demo")
	public String painelVisitante() {
		return "visitante";
	}
}

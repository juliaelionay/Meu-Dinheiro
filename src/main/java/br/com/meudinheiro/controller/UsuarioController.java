package br.com.meudinheiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.meudinheiro.model.Usuario;
import br.com.meudinheiro.service.UsuarioService;

@Controller
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/cadastro")
	public String exibirCadastro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "cadastro";
	}

	@PostMapping("/cadastro")
	public String cadastrar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {

		try {
			usuarioService.cadastrar(usuario);

			redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado com sucesso!");

			return "redirect:/login";

		} catch (IllegalArgumentException e) {

			redirectAttributes.addFlashAttribute("erro", e.getMessage());

			return "redirect:/cadastro";
		}
	}
}

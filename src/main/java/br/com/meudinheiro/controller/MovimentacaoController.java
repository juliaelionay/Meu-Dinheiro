
package br.com.meudinheiro.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.meudinheiro.model.Movimentacao;
import br.com.meudinheiro.model.Usuario;
import br.com.meudinheiro.repository.UsuarioRepository;
import br.com.meudinheiro.service.MovimentacaoService;

@Controller
public class MovimentacaoController {

	private final MovimentacaoService movimentacaoService;
	private final UsuarioRepository usuarioRepository;

	public MovimentacaoController(MovimentacaoService movimentacaoService, UsuarioRepository usuarioRepository) {
		this.movimentacaoService = movimentacaoService;
		this.usuarioRepository = usuarioRepository;
	}

	private Usuario usuarioLogado(Principal principal) {
		return usuarioRepository.findByEmail(principal.getName()).orElseThrow();
	}

	@GetMapping("/usuario/movimentacoes")
	public String listar(Model model, Principal principal) {

		Usuario usuario = usuarioLogado(principal);

		List<Movimentacao> movimentacoes = movimentacaoService.listarPorUsuario(usuario);

		BigDecimal entradas = movimentacaoService.calcularTotalPorTipo(movimentacoes, "ENTRADA");

		BigDecimal despesas = movimentacaoService.calcularTotalPorTipo(movimentacoes, "DESPESA");

		BigDecimal saldo = entradas.subtract(despesas);

		model.addAttribute("nomeUsuario", usuario.getNome());
		model.addAttribute("movimentacoes", movimentacoes);
		model.addAttribute("totalEntradas", entradas);
		model.addAttribute("totalDespesas", despesas);
		model.addAttribute("saldo", saldo);

		return "movimentacoes";
	}

	@GetMapping("/usuario/movimentacoes/nova")
	public String nova(Model model) {
		model.addAttribute("movimentacao", new Movimentacao());
		return "nova-movimentacao";
	}

	@PostMapping("/usuario/movimentacoes/salvar")
	public String salvar(@ModelAttribute Movimentacao movimentacao, Principal principal) {

		Usuario usuario = usuarioLogado(principal);
		movimentacaoService.salvar(movimentacao, usuario);

		return "redirect:/usuario/movimentacoes";
	}

	@GetMapping("/usuario/movimentacoes/editar/{id}")
	public String editar(@PathVariable Long id, Model model, Principal principal) {

		Usuario usuario = usuarioLogado(principal);

		Movimentacao movimentacao = movimentacaoService.buscarPorId(id, usuario);

		model.addAttribute("movimentacao", movimentacao);

		return "editar-movimentacao";
	}

	@PostMapping("/usuario/movimentacoes/atualizar/{id}")
	public String atualizar(@PathVariable Long id, @ModelAttribute Movimentacao movimentacao, Principal principal) {

		Usuario usuario = usuarioLogado(principal);

		movimentacaoService.atualizar(id, movimentacao, usuario);

		return "redirect:/usuario/movimentacoes";
	}

	@PostMapping("/usuario/movimentacoes/excluir/{id}")
	public String excluir(@PathVariable Long id, Principal principal) {

		Usuario usuario = usuarioLogado(principal);

		movimentacaoService.excluir(id, usuario);

		return "redirect:/usuario/movimentacoes";
	}
}

package br.com.meudinheiro.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.meudinheiro.model.Movimentacao;
import br.com.meudinheiro.model.Usuario;
import br.com.meudinheiro.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {

	private final MovimentacaoRepository movimentacaoRepository;

	public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
		this.movimentacaoRepository = movimentacaoRepository;
	}

	public Movimentacao salvar(Movimentacao movimentacao, Usuario usuario) {
		movimentacao.setUsuario(usuario);
		return movimentacaoRepository.save(movimentacao);
	}

	public List<Movimentacao> listarPorUsuario(Usuario usuario) {
		return movimentacaoRepository.findByUsuario(usuario);
	}

	public BigDecimal calcularTotalPorTipo(List<Movimentacao> movimentacoes, String tipo) {

		return movimentacoes.stream().filter(m -> tipo.equals(m.getTipo())).map(Movimentacao::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Movimentacao buscarPorId(Long id, Usuario usuario) {
		return movimentacaoRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada."));
	}

	public Movimentacao atualizar(Long id, Movimentacao dados, Usuario usuario) {

		Movimentacao movimentacao = buscarPorId(id, usuario);

		movimentacao.setDescricao(dados.getDescricao());
		movimentacao.setValor(dados.getValor());
		movimentacao.setData(dados.getData());
		movimentacao.setTipo(dados.getTipo());

		return movimentacaoRepository.save(movimentacao);
	}

	public void excluir(Long id, Usuario usuario) {
		Movimentacao movimentacao = buscarPorId(id, usuario);
		movimentacaoRepository.delete(movimentacao);
	}

}

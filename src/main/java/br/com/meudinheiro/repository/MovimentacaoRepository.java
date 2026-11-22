package br.com.meudinheiro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meudinheiro.model.Movimentacao;
import br.com.meudinheiro.model.Usuario;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

	List<Movimentacao> findByUsuario(Usuario usuario);

	Optional<Movimentacao> findByIdAndUsuario(Long id, Usuario usuario);
}

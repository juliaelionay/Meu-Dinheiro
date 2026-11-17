package br.com.meudinheiro.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.meudinheiro.model.Usuario;
import br.com.meudinheiro.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {

		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario cadastrar(Usuario usuario) {

		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("Este e-mail já está cadastrado.");
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setPerfil("USUARIO");

		return usuarioRepository.save(usuario);
	}
}

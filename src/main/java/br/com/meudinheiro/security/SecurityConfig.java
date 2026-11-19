package br.com.meudinheiro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth

				// Administrador
				.requestMatchers("/admin", "/admin/**").hasRole("ADMIN")

				// Usuário comum
				.requestMatchers("/usuario", "/usuario/**").hasAnyRole("ADMIN", "USUARIO")

				// Visitante
				.requestMatchers("/visitante", "/visitante/**").hasAnyRole("ADMIN", "VISITANTE")

				// Páginas públicas
				.requestMatchers("/", "/cadastro", "/login", "/css/**", "/js/**", "/images/**").permitAll()

				// Demais páginas exigem autenticação
				.anyRequest().authenticated())

				.formLogin(form -> form.loginPage("/login").permitAll())

				.logout(logout -> logout.logoutSuccessUrl("/"));

		return http.build();
	}
}

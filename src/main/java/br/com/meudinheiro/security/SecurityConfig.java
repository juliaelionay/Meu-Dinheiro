
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

				.requestMatchers("/admin", "/admin/**").hasRole("ADMIN")

				.requestMatchers("/usuario", "/usuario/**").hasAnyRole("ADMIN", "USUARIO")

				.requestMatchers("/visitante", "/visitante/**").hasAnyRole("ADMIN", "VISITANTE")

				.requestMatchers("/", "/cadastro", "/login", "/css/**", "/js/**", "/images/**").permitAll()

				.anyRequest().authenticated())

				.formLogin(
						form -> form.loginPage("/login").defaultSuccessUrl("/usuario/movimentacoes", true).permitAll())

				.logout(logout -> logout.logoutSuccessUrl("/"));

		return http.build();
	}
}

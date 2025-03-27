package br.edu.ifal.enology.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;

@Service
public class LoginDetailsService implements UserDetailsService {

	final UserRepository userRepository;

	public LoginDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario = userRepository.findByEmail(email);

		if (usuario == null)
			throw new AuthenticationServiceException("Este email não está cadastrado!");
		return usuario;
	}
}
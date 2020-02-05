package br.edu.ifal.enology.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;

@Repository
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmail(email);

        if (usuario != null) 
            return usuario;

        throw new UsernameNotFoundException("Usuario não encontrado!");
    }

    public Usuario getUserById(Long id) {
        Optional<Usuario> opUser = userRepository.findById(id);

        if (opUser.isPresent())
            return opUser.get();
        throw new UsernameNotFoundException("Usuario não encontrado!");
    }
}
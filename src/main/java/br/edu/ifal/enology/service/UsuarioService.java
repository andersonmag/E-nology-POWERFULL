package br.edu.ifal.enology.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;

@Service
public class UsuarioService {

    @Autowired
    UserRepository userRepository;

    public void save(@Valid Usuario usuario) {
        userRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        Optional<Usuario> opUser = userRepository.findById(id);

        if (opUser.isPresent())
            return opUser.get();
        return null;
    }

    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public int gerarCodigoAtivacao(){
        Random random = new Random();
        int codigo = random.nextInt(99999)+10000;

        return codigo;
    }

    public boolean verificarCodigo(int codigo){
        boolean verificado = findAll()
                            .stream()
                            .anyMatch(usuario -> usuario.getCodigoVerificacao() == codigo);
        return verificado;
    }

    public void ativarConta(int codigo){
            Usuario user = findAll()
                        .stream()
                        .filter(usuario -> usuario.getCodigoVerificacao() == codigo)
                        .findFirst()
                        .get();
            user.setCodigoVerificacao(0);
            user.setAtivouConta(true);
            save(user);
    }
}
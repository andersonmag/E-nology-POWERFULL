package br.edu.ifal.enology.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
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
        return opUser.orElse(null);
    }

    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Usuario> getUsuariosComMaioresPontuacoes(List<Usuario> usuarios) {

        usuarios = usuarios.stream().sorted(Comparator.comparingInt(Usuario::getPontuacaoDoAluno).reversed())
                .collect(Collectors.toList()).subList(0, usuarios.size() > 4 ? 5 : usuarios.size());
        return usuarios;
    }

    public double getMediaPontuacaoUsuarios(List<Usuario> usuarios) {
        return usuarios.stream().mapToDouble(Usuario::getPontuacaoDoAluno).average().orElse(Double.NaN);
    }

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public int gerarCodigoAtivacao() {
        Random random = new Random();
        int codigo = random.nextInt(99999) + 10000;

        return codigo;
    }

    public boolean verificarCodigo(int codigo) {
        boolean verificado = findAll().stream().anyMatch(usuario -> usuario.getCodigoVerificacao() == codigo);
        return verificado;
    }

    public void ativarConta(int codigo) {
        Usuario user = findAll().stream().filter(usuario -> usuario.getCodigoVerificacao() == codigo).findFirst().get();
        user.setCodigoVerificacao(0);
        user.setAtivouConta(true);
        save(user);
    }

    public void pontuarPorMiniGame(Usuario usuarioLogado, String miniGame) {
        if(miniGame.equals("yamato") && !usuarioLogado.isJogouAteFinalYamato()) {
            usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + 50);
            usuarioLogado.setJogouAteFinalYamato(true);

            save(usuarioLogado);
        }
        
        if(miniGame.equals("mingle") && !usuarioLogado.isJogouAteFinalMingle()) {
            usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + 50);
            usuarioLogado.setJogouAteFinalMingle(true);
    
            save(usuarioLogado);
        }
    }
}
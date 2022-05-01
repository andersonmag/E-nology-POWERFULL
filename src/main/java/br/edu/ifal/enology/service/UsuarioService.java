package br.edu.ifal.enology.service;

import java.util.ArrayList;
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

    public List<Usuario> buscarSomenteAlunos() {
        return userRepository.findByRolesIsNull();
    }

    public Integer buscaPosicaoRankingUsuario(Long idUsuario) {
        List<Usuario> usuariosOrdenadoPorPontuacao = ordenaUsuariosPorPontuacao();

        int posicao = 0;
        int ultimaPontuacao = 0;
        for (Usuario usuario : usuariosOrdenadoPorPontuacao) {
            if (ultimaPontuacao != usuario.getPontuacaoDoAluno()){
                posicao++;
                ultimaPontuacao = usuario.getPontuacaoDoAluno();
            }

            if(usuario.getId().equals(idUsuario))
                break;
        }

        return posicao;
    }

    public List<Integer> buscarPosicoesTop5UsuariosComMaioresPontuacoes() {
        List<Usuario> usuariosComMaioresPontuacoes = buscarTop5UsuariosComMaioresPontuacoes();
        List<Integer> pontuacoesEmOrdem =  usuariosComMaioresPontuacoes.stream()
                                                                        .map(u -> u.getPontuacaoDoAluno())
                                                                        .collect(Collectors.toList());
        List<Integer> ordemRank = new ArrayList<>();
        
        int posicao = 0;
        int ultimaPontuacao = 0;
        for (Integer pontuacao : pontuacoesEmOrdem) {
            if (ultimaPontuacao != pontuacao){
                posicao++;
                ultimaPontuacao = pontuacao;
            }
            ordemRank.add(posicao);
        }
        return ordemRank;
    }

    private List<Usuario> ordenaUsuariosPorPontuacao() {
        return userRepository.findByRolesIsNull()
                             .stream()
                             .sorted(
                                 Comparator.comparingInt(Usuario::getPontuacaoDoAluno).reversed())
                             .collect(Collectors.toList());
    }

    public List<Usuario> buscarTop5UsuariosComMaioresPontuacoes() {
         List<Usuario> usuariosOrdenadoPorPontuacao = ordenaUsuariosPorPontuacao();

        int menorPontuacaoTop5 = usuariosOrdenadoPorPontuacao.stream()
                                                             .mapToInt(usuario -> usuario.getPontuacaoDoAluno())
                                                             .distinct()
                                                             .limit(5).min().getAsInt();
        
        List<Usuario> top5MaioresPontuacoes = usuariosOrdenadoPorPontuacao
                                                      .stream()
                                                      .filter(usuario -> usuario.getPontuacaoDoAluno() >= menorPontuacaoTop5)
                                                      .collect(Collectors.toList());

        return top5MaioresPontuacoes;
    }

    public List<Usuario> getRankingAlunos(List<Usuario> usuarios) {
        usuarios = usuarios.stream().sorted(Comparator.comparingInt(Usuario::getPontuacaoDoAluno).reversed())
                .collect(Collectors.toList());
        return usuarios;
    }

    public double getMediaPontuacaoUsuarios() {
        return userRepository.calculateAverageScores();
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
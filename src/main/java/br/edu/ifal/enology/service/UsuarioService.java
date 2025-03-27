package br.edu.ifal.enology.service;

import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

	final UserRepository userRepository;

	public UsuarioService(UserRepository userRepository) {this.userRepository = userRepository;}

	public Usuario findById(Long id) {
		return userRepository.findById(id).orElse(null);
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
			if (ultimaPontuacao != usuario.getPontuacaoDoAluno()) {
				posicao++;
				ultimaPontuacao = usuario.getPontuacaoDoAluno();
			}

			if (usuario.getId().equals(idUsuario))
				break;
		}

		return posicao;
	}

	private List<Usuario> ordenaUsuariosPorPontuacao() {
		return userRepository.findByRolesIsNull()
			.stream()
			.sorted(
				Comparator.comparingInt(Usuario::getPontuacaoDoAluno).reversed()
			).collect(Collectors.toList());
	}

	public List<Integer> buscarPosicoesTop5UsuariosComMaioresPontuacoes() {
		final List<Usuario> usuariosComMaioresPontuacoes = buscarTop5UsuariosComMaioresPontuacoes();
		List<Integer> pontuacoesEmOrdem = usuariosComMaioresPontuacoes.stream()
			.map(Usuario::getPontuacaoDoAluno)
			.collect(Collectors.toList());
		List<Integer> ordemRank = new ArrayList<>();

		int posicao = 0;
		int ultimaPontuacao = 0;
		for (Integer pontuacao : pontuacoesEmOrdem) {
			if (ultimaPontuacao != pontuacao) {
				posicao++;
				ultimaPontuacao = pontuacao;
			}
			ordemRank.add(posicao);
		}

		return ordemRank;
	}

	public List<Usuario> buscarTop5UsuariosComMaioresPontuacoes() {
		List<Usuario> usuariosOrdenadoPorPontuacao = ordenaUsuariosPorPontuacao();

		final int menorPontuacaoTop5 = usuariosOrdenadoPorPontuacao.stream()
			.mapToInt(Usuario::getPontuacaoDoAluno)
			.distinct()
			.limit(5).min().getAsInt();

		return usuariosOrdenadoPorPontuacao
			.stream()
			.filter(usuario -> usuario.getPontuacaoDoAluno() >= menorPontuacaoTop5)
			.collect(Collectors.toList());
	}

	public List<Usuario> getRankingAlunos(List<Usuario> usuarios) {
		usuarios = usuarios.stream().sorted(Comparator.comparingInt(Usuario::getPontuacaoDoAluno).reversed())
			.collect(Collectors.toList());
		return usuarios;
	}

	public double getMediaPontuacaoUsuarios() {
		return userRepository.calculateAverageScores();
	}

	public int gerarCodigoAtivacao() {
		Random random = new Random();
		return random.nextInt(99999) + 10000;
	}

	public boolean verificarCodigo(int codigo) {
		return findAll().stream().anyMatch(usuario -> usuario.getCodigoVerificacao() == codigo);
	}

	public List<Usuario> findAll() {
		return userRepository.findAll();
	}

	public void ativarConta(int codigo) {
		Usuario user = findAll().stream().filter(usuario -> usuario.getCodigoVerificacao() == codigo).findFirst().get();
		user.setCodigoVerificacao(0);
		user.setAtivouConta(true);
		save(user);
	}

	public void save(@Valid Usuario usuario) {
		userRepository.save(usuario);
	}

	public void pontuarPorMiniGame(Usuario usuarioLogado, String miniGame) {
		final int aumentoPadrao = 50;

		if (miniGame.equals("yamato") && !usuarioLogado.isJogouAteFinalYamato()) {
			usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + aumentoPadrao);
			usuarioLogado.setJogouAteFinalYamato(true);

			save(usuarioLogado);
		}
		if (miniGame.equals("mingle") && !usuarioLogado.isJogouAteFinalMingle()) {
			usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + aumentoPadrao);
			usuarioLogado.setJogouAteFinalMingle(true);

			save(usuarioLogado);
		}
	}
}
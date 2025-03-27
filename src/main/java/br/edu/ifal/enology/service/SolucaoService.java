package br.edu.ifal.enology.service;

import br.edu.ifal.enology.model.Solucao;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.SolucaoRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class SolucaoService {

	final SolucaoRepository solucaoRepository;

	public SolucaoService(SolucaoRepository solucaoRepository) {
		this.solucaoRepository = solucaoRepository;
	}

	public void save(@Valid Solucao solucao) {
		solucaoRepository.save(solucao);
	}

	public Long getQuantidadeRespostasAlunoPorCondicao(Usuario aluno, boolean condicao) {
		List<Solucao> solucoesUsuario = buscarPorUsuario(aluno);
		return solucoesUsuario.stream().filter(solucao -> solucao.isAcertou() == condicao).count();
	}

	public List<Solucao> buscarPorUsuario(Usuario aluno) {
		return solucaoRepository.findByAluno(aluno);
	}
}
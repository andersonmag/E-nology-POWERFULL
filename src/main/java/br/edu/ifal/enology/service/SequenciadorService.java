package br.edu.ifal.enology.service;

import br.edu.ifal.enology.model.*;
import br.edu.ifal.enology.repository.PalavraRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SequenciadorService {

	private final PalavraRepository palavraRepository;
	private final TarefaService tarefaService;
	private final SolucaoService solucaoService;
	private final ConteudoService conteudoService;

	public SequenciadorService(
		PalavraRepository palavraRepository, TarefaService tarefaService, SolucaoService solucaoService,
		ConteudoService conteudoService
	) {
		this.palavraRepository = palavraRepository;
		this.tarefaService = tarefaService;
		this.solucaoService = solucaoService;
		this.conteudoService = conteudoService;
	}

	public boolean isProximoConteudo(
		Conteudo conteudo, List<Tarefa> tarefasRespondidasCorretamente, Integer faseAtual
	) {
		if (tarefasRespondidasCorretamente.isEmpty() && conteudo.getId() == 1L) {
			return true;
		}
		else if (verificarSolucaoExistenteComConteudo(conteudo, tarefasRespondidasCorretamente))
			return true;

		return faseAtual.equals(conteudo.getId().intValue());
	}

	public boolean verificarSolucaoExistenteComConteudo(
		Conteudo conteudo, List<Tarefa> tarefasRespondidasCorretamente
	) {
		return tarefasRespondidasCorretamente.stream().anyMatch(tarefa -> tarefa.getConteudo().equals(conteudo));
	}

	public Tarefa buscarTarefa(Usuario usuario, Conteudo conteudo) {
		List<Solucao> solucoesDoAluno = solucaoService.buscarPorUsuario(usuario);
		List<Tarefa> tarefasRespondidasCorretamente = filtrarTarefasRespondidas(solucoesDoAluno);
		List<Tarefa> tarefasConteudo = tarefaService.buscarPorConteudo(conteudo).subList(0, 3);

		if (solucoesDoAluno.isEmpty() && !conteudo.getId().equals(1L)) {
			conteudo = conteudoService.findById(1L);
			tarefasConteudo = tarefaService.buscarPorConteudo(conteudo).subList(0, 3);
		}

		return selecionarTarefa(tarefasConteudo, tarefasRespondidasCorretamente);
	}

	public List<Tarefa> filtrarTarefasRespondidas(List<Solucao> solucoesDoAlunoDoConteudo) {
		List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

		for (Solucao solucao : solucoesDoAlunoDoConteudo) {
			if (solucao.isAcertou())
				tarefasRespondidasCorretamente.add(solucao.getTarefa());
		}
		return tarefasRespondidasCorretamente;
	}

	private Tarefa selecionarTarefa(List<Tarefa> tarefasConteudo, List<Tarefa> tarefasRespondidasCorretamente) {
		final List<Tarefa> tarefasRestantesTexto = new ArrayList<>();
		final List<Tarefa> tarefasRestantesGeral = new ArrayList<>();

		final Random numeroAleatorio = new Random();

		for (Tarefa tarefa : tarefasConteudo) {
			if (tarefasRespondidasCorretamente.isEmpty() || !tarefasRespondidasCorretamente.contains(tarefa)) {
				if (tarefa.getTipoTarefa() == TipoTarefa.MULTIPLA_ESCOLHA_TEXTO) {
					tarefasRestantesTexto.add(tarefa);
					continue;
				}
				tarefasRestantesGeral.add(tarefa);
			}
		}

		if (verificarConclusaoConteudo(tarefasRespondidasCorretamente, tarefasConteudo))
			return null;

		if (!tarefasRestantesTexto.isEmpty()) {
			int indexTarefa = numeroAleatorio.nextInt(tarefasRestantesTexto.size());
			return tarefasRestantesTexto.get(indexTarefa);
		}

		int indexTarefa = numeroAleatorio.nextInt(tarefasRestantesGeral.size());
		return tarefasRestantesGeral.get(indexTarefa);
	}

	public boolean verificarConclusaoConteudo(
		List<Tarefa> tarefasRespondidasCorretamente,
		List<Tarefa> tarefasConteudo
	) {
		return new HashSet<>(tarefasRespondidasCorretamente).containsAll(tarefasConteudo);
	}

	public List<Tarefa> filtrarTarefasRespondidasPorConteudo(
		List<Solucao> solucoesDoAlunoDoConteudo, Conteudo conteudo
	) {

		List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();
		for (Solucao solucao : solucoesDoAlunoDoConteudo) {
			if (solucao.isAcertou() && solucao.getTarefa().getConteudo().equals(conteudo))
				tarefasRespondidasCorretamente.add(solucao.getTarefa());
		}
		return tarefasRespondidasCorretamente;
	}

	public Conteudo pegarConteudoAleatorio(Palavra respostaDaTarefa) {
		Random numeroAleatorio = new Random();
		List<Conteudo> todosConteudos = conteudoService.findAll();
		int indexConteudo;

		for (Conteudo conteudo : todosConteudos) {
			if (respostaDaTarefa.getConteudos().contains(conteudo)) {
				return conteudo;
			}
		}
		indexConteudo = numeroAleatorio.nextInt(todosConteudos.size());
		return todosConteudos.get(indexConteudo);
	}

	public List<Palavra> buscarPalavrasPorConteudoTexto(int idTarefa) {
		Conteudo conteudo = new Conteudo();
		final Map<Integer, Long> tarefaConteudoIds = new HashMap<>();
		tarefaConteudoIds.put(1, 6L);
		tarefaConteudoIds.put(7, 7L);
		tarefaConteudoIds.put(8, 8L);
		tarefaConteudoIds.put(9, 9L);
		tarefaConteudoIds.put(10, 10L);

		conteudo.setId(tarefaConteudoIds.get(idTarefa));
		List<Palavra> palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);

		Collections.shuffle(palavrasFiltradasPorConteudo);
		return palavrasFiltradasPorConteudo;
	}

	public List<Palavra> buscarPalavrasPorConteudo(Conteudo conteudo, Palavra respostaDaTarefa) {
		List<Palavra> palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
		List<Palavra> palavrasComTamanhoCorreto = new ArrayList<>();
		int tamanhoMaximoDaLista = 3;

		Collections.shuffle(palavrasFiltradasPorConteudo);

		for (Palavra palavra : palavrasFiltradasPorConteudo) {
			if (palavrasComTamanhoCorreto.size() < tamanhoMaximoDaLista && palavra != respostaDaTarefa) {
				palavrasComTamanhoCorreto.add(palavra);
			}
		}
		palavrasComTamanhoCorreto.add(respostaDaTarefa);
		Collections.shuffle(palavrasComTamanhoCorreto);
		return palavrasComTamanhoCorreto;
	}
}
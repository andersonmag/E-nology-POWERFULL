package br.edu.ifal.enology.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Solucao;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.SolucaoRepository;

@Service
public class SolucaoService {

    @Autowired
    SolucaoRepository solucaoRepository;

    public void save(@Valid Solucao solucao) {
        solucaoRepository.save(solucao);
    }

    public List<Solucao> findByAluno(Usuario aluno) {
        return solucaoRepository.findByAluno(aluno);
    }
}
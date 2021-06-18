package br.edu.ifal.enology.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.Texto;
import br.edu.ifal.enology.repository.TarefaRepository;
import br.edu.ifal.enology.repository.TextoTarefaRepository;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private TextoTarefaRepository textoTarefaRepository;

    public void save(@Valid Tarefa tarefa) {
        
        if(tarefa.getTexto() != null && tarefa.getTexto().getConteudo() != null) {
            Texto textoSalvo = textoTarefaRepository.save(tarefa.getTexto());
            tarefa.setTexto(textoSalvo);
        }

        tarefaRepository.save(tarefa);
    }

    public Tarefa findById(Long id) {
       return tarefaRepository.findById(id).orElse(null);  
    }

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> buscarPorConteudo(Conteudo conteudo) {
        return tarefaRepository.findByConteudo(conteudo);
    }
}
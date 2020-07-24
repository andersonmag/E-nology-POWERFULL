package br.edu.ifal.enology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifal.enology.exception.ClassNotFoundException;
import br.edu.ifal.enology.model.Turma;
import br.edu.ifal.enology.repository.TurmaRepository;

@Service
public class TurmaService {
    
    @Autowired
    private TurmaRepository turmaRepository;

    public Turma findById(Long id) {
        return turmaRepository.findById(id)
                              .orElseThrow(() -> new ClassNotFoundException("Turma não encontrada!"));
    }


}
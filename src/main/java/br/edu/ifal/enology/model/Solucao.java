package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Solucao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Usuario aluno;
    @OneToOne
    private Tarefa tarefa;
    private String resposta;
    private boolean acertou;
    private int pontuacao;
}
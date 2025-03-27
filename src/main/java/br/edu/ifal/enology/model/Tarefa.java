package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Tarefa {

    public TipoTarefa tipoTarefa;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pontuacao;
    private Nivel nivel;
    private String enunciado;

    @OneToOne
    private Texto texto;

    @ManyToOne
    private Conteudo conteudo;

    @ManyToOne
    private Palavra resposta;
}
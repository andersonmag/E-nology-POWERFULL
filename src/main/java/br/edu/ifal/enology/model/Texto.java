package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Texto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String conteudo;
    private String fonteConteudo;

    @OneToOne(orphanRemoval = true)
    private Imagem imagem;

    private String fonteImagem;
}

package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String portugues;
    private String ingles;
    // private byte[] imagem;
    private String definicao;
    private boolean termoTecnico;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Conteudo> conteudos;
}
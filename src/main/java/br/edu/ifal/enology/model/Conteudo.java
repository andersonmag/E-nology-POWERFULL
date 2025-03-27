package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Getter @Setter
public class Conteudo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

    @ManyToMany(mappedBy = "conteudos", cascade = CascadeType.ALL)
    private List<Palavra> palavras;
}
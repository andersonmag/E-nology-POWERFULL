package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String semestre;
    private String ano;
    private String nome;

    @ManyToMany
    private List<Usuario> usuarios;
}
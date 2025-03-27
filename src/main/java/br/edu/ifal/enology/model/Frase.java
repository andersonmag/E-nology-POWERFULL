package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Getter @Setter
public class Frase{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ingles;
    private String portugues;

    @ManyToMany
    private List<Palavra> palavras;
}
package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Imagem {

    @Id
    @GeneratedValue
    private Long id;

    @Type(type = "org.hibernate.type.ImageType")
    private byte[] dados;
    private String nome;
    private String tipo;
}
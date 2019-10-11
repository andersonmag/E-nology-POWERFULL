package br.edu.ifal.enology.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Solucao{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Usuario aluno;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Tarefa correcao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Tarefa getCorrecao() {
        return correcao;
    }

    public void setCorrecao(Tarefa correcao) {
        this.correcao = correcao;
    }
    
}
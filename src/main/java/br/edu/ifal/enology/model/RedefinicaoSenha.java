package br.edu.ifal.enology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "redefinicao_senha_usuarios")
@Getter
@Setter
public class RedefinicaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Usuario usuario;

    @Column(name = "token_usuario", unique = true,
            updatable = false, nullable = false)
    private String token;

    @Column(name = "tempo_limite", updatable = false, unique = false)
    private LocalDateTime timeout;

    public RedefinicaoSenha() {
        UUID uuid = UUID.randomUUID();
        this.token = uuid.toString().substring(0, 23).replaceAll("-", "");
    }

    public void setTimeout(LocalDateTime timeout) {
        Long duracaoToken = 12L; //12 Horas
        this.timeout = timeout.plusHours(duracaoToken);
    }

}
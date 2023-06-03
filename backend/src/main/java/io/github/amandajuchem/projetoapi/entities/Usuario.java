package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Setor;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

/**
 * The type User.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_usuarios")
public class Usuario extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100)
    private String nome;

    @CPF
    @Column(name = "cpf", length = 11)
    private String cpf;

    @NotEmpty
    @Column(name = "senha")
    private String senha;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Column(name = "setor", length = 20)
    @Enumerated(EnumType.STRING)
    private Setor setor;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagem foto;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
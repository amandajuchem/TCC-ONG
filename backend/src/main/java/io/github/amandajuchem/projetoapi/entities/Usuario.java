package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type User.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_usuarios")
public class Usuario extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome")
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
    @Enumerated(EnumType.STRING)
    @Column(name = "setor")
    private Setor setor;

    @OneToOne(mappedBy = "usuario")
    @JsonManagedReference
    private Imagem foto;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
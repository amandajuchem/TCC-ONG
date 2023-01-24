package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Setor;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type User.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
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
    @JsonManagedReference(value = "jsonReferenceUsuario")
    private Imagem foto;
}
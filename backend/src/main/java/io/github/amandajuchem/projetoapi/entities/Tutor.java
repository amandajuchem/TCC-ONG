package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Tutor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tutores")
public class Tutor extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome")
    private String nome;

    @NotEmpty
    @CPF
    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "rg", length = 13)
    private String rg;

    @NotEmpty
    @Column(name = "telefone", length = 11)
    private String telefone;

    @NotNull
    @Column(name = "situacao")
    @Enumerated(value = EnumType.STRING)
    private Situacao situacao;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @OneToOne(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceTutor")
    private Imagem foto;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
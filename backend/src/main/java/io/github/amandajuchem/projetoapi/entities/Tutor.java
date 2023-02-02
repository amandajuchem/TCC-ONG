package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The type Tutor.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tutores")
public class Tutor extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100)
    private String nome;

    @NotEmpty
    @CPF
    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "rg", length = 13)
    private String rg;

    @NotNull
    @Column(name = "situacao", length = 10)
    @Enumerated(value = EnumType.STRING)
    private Situacao situacao;

    @Valid
    @OneToOne(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceFotoTutor")
    private Imagem foto;

    @Valid
    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceTelefonesTutor")
    private Set<Telefone> telefones;

    @Valid
    @OneToOne(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceEnderecoTutor")
    private Endereco endereco;

    @Valid
    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceAdocoesTutor")
    private Set<Adocao> adocoes;

    @Valid
    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference(value = "jsonReferenceObservacoesTutor")
    private Set<Observacao> observacoes;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
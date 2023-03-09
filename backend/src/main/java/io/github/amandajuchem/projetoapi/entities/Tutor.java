package io.github.amandajuchem.projetoapi.entities;

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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagem foto;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @Valid
    @OneToMany(mappedBy = "tutor")
    private Set<Adocao> adocoes;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Observacao> observacoes;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
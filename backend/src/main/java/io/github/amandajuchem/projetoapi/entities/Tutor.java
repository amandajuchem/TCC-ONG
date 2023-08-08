package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tutores")
public class Tutor extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100)
    private String nome;

    @NotEmpty
    @CPF
    @Column(name = "cpf", length = 11, unique = true)
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
    @ToString.Exclude
    private List<Telefone> telefones;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference("referenceAdocaoTutor")
    @ToString.Exclude
    private Set<Adocao> adocoes;

    @OneToMany(orphanRemoval = true, mappedBy = "tutor")
    @JsonManagedReference("referenceObservacaoTutor")
    @ToString.Exclude
    private Set<Observacao> observacoes;
}
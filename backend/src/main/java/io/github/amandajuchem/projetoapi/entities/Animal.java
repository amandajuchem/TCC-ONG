package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Especie;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The type Animal.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_animais")
public class Animal extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 50)
    private String nome;

    @NotNull
    @Column(name = "idade")
    private Integer idade;

    @NotNull
    @Column(name = "especie", length = 10)
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @NotNull
    @Column(name = "sexo", length = 5)
    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;

    @NotEmpty
    @Column(name = "raca", length = 50)
    private String raca;

    @NotEmpty
    @Column(name = "cor", length = 50)
    private String cor;

    @NotNull
    @Column(name = "porte", length = 10)
    @Enumerated(value = EnumType.STRING)
    private Porte porte;

    @NotNull
    @Column(name = "situacao", length = 10)
    @Enumerated(value = EnumType.STRING)
    private Situacao situacao;

    @Valid
    @OneToOne(mappedBy = "animal")
    @JsonManagedReference(value = "jsonReferenceFotoAnimal")
    private Imagem foto;

    @Valid
    @OneToOne(mappedBy = "animal")
    @JsonManagedReference(value = "jsonReferenceFichaMedicaAnimal")
    private FichaMedica fichaMedica;

    @Valid
    @OneToMany(mappedBy = "animal")
    @JsonManagedReference(value = "jsonReferenceAdocoesAnimal")
    private Set<Adocao> adocoes;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
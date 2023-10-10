package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Especie;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
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

    @Column(name = "raca", length = 50)
    private String raca;

    @Column(name = "cor", length = 50)
    private String cor;

    @Column(name = "porte", length = 10)
    @Enumerated(value = EnumType.STRING)
    private Porte porte;

    @NotNull
    @Column(name = "situacao", length = 10)
    @Enumerated(value = EnumType.STRING)
    private Situacao situacao;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Arquivo foto;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaMedica fichaMedica;

    @OneToMany(orphanRemoval = true, mappedBy = "animal")
    @JsonManagedReference("referenceAdocaoAnimal")
    @ToString.Exclude
    private Set<Adocao> adocoes;
}
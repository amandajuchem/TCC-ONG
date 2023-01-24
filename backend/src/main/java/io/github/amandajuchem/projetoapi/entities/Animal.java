package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * The type Animal.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_animais")
public class Animal extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "idade")
    private Integer idade;

    @NotEmpty
    @Column(name = "especie")
    private String especie;

    @Column(name = "local")
    private String local;

    @Column(name = "local_adocao")
    private String localAdocao;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @NotEmpty
    @Column(name = "raca")
    private String raca;

    @Column
    private LocalDate dataAdocao;

    @Column
    private LocalDate dataResgate;

    @NotEmpty
    @Column(name = "cor")
    private String cor;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "porte")
    private Porte porte;

    @NotNull
    @Column(name = "castrado")
    private Boolean castrado;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "situacao")
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToOne(mappedBy = "animal")
    @JsonManagedReference(value = "jsonReferenceAnimal")
    private Imagem foto;
}
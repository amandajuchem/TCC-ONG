package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.amandajuchem.projetoapi.enums.Local;
import io.github.amandajuchem.projetoapi.enums.LocalAdocao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Adocao.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_adocoes")
public class Adocao extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "local", length = 10)
    @Enumerated(EnumType.STRING)
    private Local local;

    @Column(name = "local_adocao", length = 10)
    @Enumerated(EnumType.STRING)
    private LocalAdocao localAdocao;

    @NotNull
    @Column(name = "vale_castracao")
    private Boolean valeCastracao;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference(value = "jsonReferenceAdocoesAnimal")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference(value = "jsonReferenceAdocoesTutor")
    private Tutor tutor;

    @Valid
    @OneToMany(mappedBy = "adocao")
    @JsonManagedReference(value = "jsonReferenceTermoResponsabilidadeAdocao")
    private Set<Imagem> termoResponsabilidade;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
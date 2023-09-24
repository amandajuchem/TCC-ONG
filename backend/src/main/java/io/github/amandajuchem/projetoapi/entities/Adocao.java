package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.amandajuchem.projetoapi.enums.Local;
import io.github.amandajuchem.projetoapi.enums.LocalAdocao;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
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
    @JsonBackReference("referenceAdocaoAnimal")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference("referenceAdocaoTutor")
    private Tutor tutor;

    @ManyToOne
    private FeiraAdocao feiraAdocao;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Imagem> termoResponsabilidade;
}
package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_fichas_medicas")
public class FichaMedica extends AbstractEntity {

    @NotEmpty
    @Column(name = "comorbidades", columnDefinition = "TEXT")
    private String comorbidades;

    @OneToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference(value = "jsonReferenceFichaMedicaAnimal")
    private Animal animal;
}
package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_telefones")
public class Telefone extends AbstractEntity {

    @NotEmpty
    @Column(name = "numero", length = 11)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference(value = "jsonReferenceTelefonesTutor")
    private Tutor tutor;
}
package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_observacoes")
public class Observacao extends AbstractEntity {

    @NotEmpty
    @Column(columnDefinition = "VARCHAR")
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference("referenceObservacaoTutor")
    private Tutor tutor;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
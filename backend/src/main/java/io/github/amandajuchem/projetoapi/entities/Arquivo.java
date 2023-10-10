package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_arquivos")
public class Arquivo extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 25, unique = true)
    private String nome;

    @PostPersist
    @PostUpdate
    private void postSave() {

        FileUtils.FILES.forEach((key, value) -> {

            try {
                FileUtils.save(key, value, FileUtils.FILES_DIRECTORY);
            } catch (IOException e) {
                throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
            }
        });

        FileUtils.FILES.clear();
    }

    @PostRemove
    private void postDelete() {
        FileUtils.delete(nome, FileUtils.FILES_DIRECTORY);
    }
}
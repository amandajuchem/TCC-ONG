package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * The type Imagem.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_imagens")
public class Imagem extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 25)
    private String nome;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @PostPersist
    @PostUpdate
    private void postSave() throws IOException {
        FileUtils.save(nome, FileUtils.FILE, FileUtils.IMAGES_DIRECTORY);
        FileUtils.FILE = null;
    }

    @PostRemove
    private void postDelete() {
        FileUtils.delete(nome, FileUtils.IMAGES_DIRECTORY);
    }
}
package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
@Tag(name = "Imagem", description = "Endpoints para gerenciamento de imagens")
public class ImagemController {

    private final ServletContext servletContext;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String nome) throws FileNotFoundException {

        if (nome != null && !nome.isEmpty()) {

            var file = FileUtils.find(nome, FileUtils.IMAGES_DIRECTORY);
            var mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
            var resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity
                    .status(OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        }

        throw new ObjectNotFoundException(MessageUtils.FILE_NOT_FOUND);
    }
}
package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.springframework.http.HttpStatus.OK;

/**
 * The type Imagem controller.
 */
@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
public class ImagemController {

    private final ServletContext servletContext;

    /**
     * Search response entity.
     *
     * @param nome the nome
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String nome) throws FileNotFoundException {

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
}
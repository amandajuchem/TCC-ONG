package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
@Tag(name = "Imagens", description = "Endpoints for images management")
public class ImagemController {

    private final ServletContext servletContext;

    @GetMapping("/search")
    @ResponseStatus(OK)
    public ResponseEntity<InputStreamResource> search(@RequestParam String value) throws FileNotFoundException {

        final var file = FileUtils.find(value, FileUtils.IMAGES_DIRECTORY);
        final var mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
        final var resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity
                .status(OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
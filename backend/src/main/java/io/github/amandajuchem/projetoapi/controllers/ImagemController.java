package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
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

/**
 * Controller class for managing images.
 * Provides endpoints for searching.
 */
@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
@Tag(name = "Imagens", description = "Endpoints for images management")
public class ImagemController {

    private final ServletContext servletContext;

    /**
     * Search for image by value.
     *
     * @param value The value to search for.
     * @return The ResponseEntity containing the image file.
     * @throws FileNotFoundException If the image file is not found.
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String value) throws FileNotFoundException {

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
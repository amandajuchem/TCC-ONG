package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    private final FacadeService facade;
    private final ServletContext servletContext;

    @Autowired
    public ImagemController(FacadeService facade, ServletContext servletContext) {
        this.facade = facade;
        this.servletContext = servletContext;
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = false) String nome) {

        if (nome != null) {
            try {
                File file = FileUtils.find(nome, FileUtils.IMAGES_DIRECTORY);
                MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                return ResponseEntity
                        .status(OK)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                        .contentType(mediaType)
                        .contentLength(file.length())
                        .body(resource)
                        ;
            } catch (Exception ex) {
            }
        }

        throw new ObjectNotFoundException(MessageUtils.IMAGEM_NOT_FOUND);
    }
}
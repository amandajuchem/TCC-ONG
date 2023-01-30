package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MediaTypeUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.FileInputStream;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ImagemController {

    private final ServletContext servletContext;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String nome) {

        if (nome != null) {

            try {

                var file = FileUtils.find(nome, FileUtils.IMAGES_DIRECTORY);
                var mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
                var resource = new InputStreamResource(new FileInputStream(file));

                return ResponseEntity
                        .status(OK)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                        .contentType(mediaType)
                        .contentLength(file.length())
                        .body(resource)
                        ;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
            }
        }

        throw new ObjectNotFoundException(MessageUtils.IMAGEM_NOT_FOUND);
    }
}
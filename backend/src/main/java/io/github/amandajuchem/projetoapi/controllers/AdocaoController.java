package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AdocaoService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Adocao controller.
 */
@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
public class AdocaoController {

    private final AdocaoService service;

    /**
     * Delete.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all.
     *
     * @param animalId  the animal id
     * @param tutorId   the tutor id
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) UUID animalId,
                                     @RequestParam(required = false) UUID tutorId,
                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (animalId != null || tutorId != null) {
            var adocoes = service.findAll(animalId, tutorId, page, size, sort, direction).map(AdocaoDTO::toDTO);
            return ResponseEntity.status(OK).body(adocoes);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Save.
     *
     * @param adocao                the adocao
     * @param termoResponsabilidade the termo responsabilidade
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     * @throws InterruptedException  the interrupted exception
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Adocao adocao,
                                  @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {

        if (termoResponsabilidade != null) {

            for (MultipartFile termo : termoResponsabilidade) {

                var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(termo))
                        .build();

                if (adocao.getTermoResponsabilidade() == null) {
                    adocao.setTermoResponsabilidade(new ArrayList<>());
                }

                adocao.getTermoResponsabilidade().add(imagem);
                FileUtils.FILES.put(imagem.getNome(), termo);
                Thread.sleep(10);
            }
        }

        adocao = service.save(adocao);
        return ResponseEntity.status(CREATED).body(adocao);
    }

    /**
     * Update.
     *
     * @param id                    the id
     * @param adocao                the adocao
     * @param termoResponsabilidade the termo responsabilidade
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     * @throws InterruptedException  the interrupted exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Adocao adocao,
                                    @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {

        if (adocao.getId().equals(id)) {

            if (termoResponsabilidade != null) {

                for (MultipartFile termo : termoResponsabilidade) {

                    var imagem = Imagem.builder()
                            .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(termo))
                            .build();

                    if (adocao.getTermoResponsabilidade() == null) {
                        adocao.setTermoResponsabilidade(new ArrayList<>());
                    }

                    adocao.getTermoResponsabilidade().add(imagem);
                    FileUtils.FILES.put(imagem.getNome(), termo);
                    Thread.sleep(10);
                }
            }

            adocao = service.save(adocao);
            return ResponseEntity.status(OK).body(adocao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}
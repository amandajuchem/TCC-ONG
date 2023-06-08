package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AdocaoService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
@Tag(name = "Adoção", description = "Endpoints para gerenciamento de adoções")
public class AdocaoController implements AbstractController<Adocao, AdocaoDTO> {

    private final AdocaoService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AdocaoDTO>> findAll(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) String sort,
                                                   @RequestParam(required = false) String direction) {

        final var adocoes = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(adocoes);
    }

    @Override
    public ResponseEntity<AdocaoDTO> findById(UUID id) {
        final var adocao = service.findById(id);
        return ResponseEntity.status(OK).body(adocao);
    }

    @Operation(summary = "Save", description = "Save an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdocaoDTO> save(@RequestPart @Valid Adocao adocao,
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

        return save(adocao);
    }

    @Override
    public ResponseEntity<AdocaoDTO> save(Adocao adocao) {
        final var adocaoSaved = service.save(adocao);
        return ResponseEntity.status(CREATED).body(adocaoSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AdocaoDTO>> search(@RequestParam String value,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                  @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var adocoes = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(adocoes);
    }

    @Operation(summary = "Update", description = "Update an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdocaoDTO> update(@PathVariable UUID id,
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

            return update(id, adocao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public ResponseEntity<AdocaoDTO> update(UUID id, Adocao adocao) {
        final var adocaoSaved = service.save(adocao);
        return ResponseEntity.status(OK).body(adocaoSaved);
    }
}
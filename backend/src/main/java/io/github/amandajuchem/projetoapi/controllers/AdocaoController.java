package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Arquivo;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AdocaoService;
import io.github.amandajuchem.projetoapi.services.AnimalService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
@Tag(name = "Adoções", description = "Endpoints for adoptions management")
public class AdocaoController implements AbstractController<Adocao, AdocaoDTO> {

    private final AdocaoService adocaoService;
    private final AnimalService animalService;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        adocaoService.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<AdocaoDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                   @RequestParam(required = false, defaultValue = "desc") String direction) {
        return adocaoService.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AdocaoDTO findById(@PathVariable UUID id) {
        return adocaoService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public AdocaoDTO save(@RequestPart @Valid Adocao adocao,
                          @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {
        handleTermoResponsabilidade(adocao, termoResponsabilidade);
        return save(adocao);
    }

    @Override
    public AdocaoDTO save(Adocao adocao) {
        return handleAdocao(adocao);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<AdocaoDTO> search(@RequestParam String value,
                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                  @RequestParam(required = false, defaultValue = "desc") String direction) {
        return adocaoService.search(value, page, size, sort, direction);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public AdocaoDTO update(@PathVariable UUID id,
                            @RequestPart @Valid Adocao adocao,
                            @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {

        if (adocao.getId().equals(id)) {
            handleTermoResponsabilidade(adocao, termoResponsabilidade);
            return update(id, adocao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public AdocaoDTO update(UUID id, Adocao adocao) {
        return handleAdocao(adocao);
    }

    private AdocaoDTO handleAdocao(Adocao adocao) {
        final var animalId = adocao.getAnimal().getId();
        adocao = AdocaoDTO.toAdocao(adocaoService.save(adocao));
        final var animal = AnimalDTO.toAnimal(animalService.findById(animalId));

        animal.setSituacao(Situacao.ADOTADO);
        animal.setAdocoes(Collections.emptySet());
        animalService.save(animal);
        adocao.setAnimal(animal);

        return AdocaoDTO.toDTO(adocao);
    }

    private void handleTermoResponsabilidade(Adocao adocao, List<MultipartFile> termoResponsabilidade) throws InterruptedException {

        if (termoResponsabilidade != null) {

            for (MultipartFile termo : termoResponsabilidade) {

                final var imagem = Arquivo.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(Objects.requireNonNull(termo.getOriginalFilename())))
                        .build();

                if (adocao.getTermoResponsabilidade() == null) {
                    adocao.setTermoResponsabilidade(new ArrayList<>());
                }

                adocao.getTermoResponsabilidade().add(imagem);
                FileUtils.FILES.put(imagem.getNome(), termo);
                Thread.sleep(10);
            }
        }
    }
}
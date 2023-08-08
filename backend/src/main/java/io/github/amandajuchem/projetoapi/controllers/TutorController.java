package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.TutorService;
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
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tutores")
@RequiredArgsConstructor
@Tag(name = "Tutores", description = "Endpoints for tutors management")
public class TutorController implements AbstractController<Tutor, TutorDTO> {

    private final TutorService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<TutorDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                  @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TutorDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public TutorDTO save(@RequestPart @Valid Tutor tutor,
                         @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {
        handleFoto(tutor, foto);
        return save(tutor);
    }

    @Override
    public TutorDTO save(Tutor tutor) {
        return service.save(tutor);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<TutorDTO> search(@RequestParam String value,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "nome") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public TutorDTO update(@PathVariable UUID id,
                           @RequestPart @Valid Tutor tutor,
                           @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (tutor.getId().equals(id)) {
            handleFoto(tutor, foto);
            return update(id, tutor);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public TutorDTO update(UUID id, Tutor tutor) {
        return service.save(tutor);
    }

    private void handleFoto(Tutor tutor, MultipartFile foto) {

        if (foto != null) {

            final var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(Objects.requireNonNull(foto.getOriginalFilename())))
                    .build();

            tutor.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }
    }
}
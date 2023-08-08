package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
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
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor
@Tag(name = "Animais", description = "Endpoints for animals management")
public class AnimalController implements AbstractController<Animal, AnimalDTO> {

    private final AnimalService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<AnimalDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "nome") String sort,
                                   @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AnimalDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public AnimalDTO save(@RequestPart @Valid Animal animal,
                          @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {
        handleFoto(animal, foto);
        return save(animal);
    }

    @Override
    public AnimalDTO save(Animal animal) {
        return service.save(animal);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<AnimalDTO> search(@RequestParam String value,
                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                  @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public AnimalDTO update(@PathVariable UUID id,
                            @RequestPart @Valid Animal animal,
                            @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (animal.getId().equals(id)) {
            handleFoto(animal, foto);
            return update(id, animal);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public AnimalDTO update(UUID id, Animal animal) {
        return service.save(animal);
    }

    private void handleFoto(Animal animal, MultipartFile foto) {

        if (foto != null) {

            final var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(Objects.requireNonNull(foto.getOriginalFilename())))
                    .build();

            animal.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }
    }
}

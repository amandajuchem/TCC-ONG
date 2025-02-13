package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.ExameRealizado;
import io.github.amandajuchem.projetoapi.entities.Arquivo;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AtendimentoService;
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
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
@Tag(name = "Atendimentos", description = "Endpoints for treatments management")
public class AtendimentoController implements AbstractController<Atendimento, AtendimentoDTO> {

    private final AtendimentoService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<AtendimentoDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                        @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AtendimentoDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public AtendimentoDTO save(@RequestPart @Valid Atendimento atendimento,
                               @RequestPart(required = false) List<MultipartFile> examesRealizados) throws FileNotFoundException, InterruptedException {
        handleExamesRealizados(atendimento, examesRealizados);
        return save(atendimento);
    }

    @Override
    public AtendimentoDTO save(Atendimento atendimento) {
        return service.save(atendimento);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<AtendimentoDTO> search(@RequestParam String value,
                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                       @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public AtendimentoDTO update(@PathVariable UUID id,
                                 @RequestPart @Valid Atendimento atendimento,
                                 @RequestPart(required = false) List<MultipartFile> examesRealizados) throws FileNotFoundException, InterruptedException {

        if (atendimento.getId().equals(id)) {
            handleExamesRealizados(atendimento, examesRealizados);
            return update(id, atendimento);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public AtendimentoDTO update(UUID id, Atendimento atendimento) {
        return service.save(atendimento);
    }

    private void handleExamesRealizados(Atendimento atendimento, List<MultipartFile> files) throws InterruptedException {

        if (files != null) {

            for (MultipartFile file : files) {

                for (ExameRealizado exameRealizado : atendimento.getExamesRealizados()) {

                    final var fileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];

                    if (exameRealizado.getExame().getNome().contains(fileName)) {

                        final var arquivo = Arquivo.builder()
                                .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(Objects.requireNonNull(file.getOriginalFilename())))
                                .build();

                        exameRealizado.setArquivo(arquivo);
                        FileUtils.FILES.put(arquivo.getNome(), file);
                        Thread.sleep(10);
                    }
                }
            }
        }
    }
}
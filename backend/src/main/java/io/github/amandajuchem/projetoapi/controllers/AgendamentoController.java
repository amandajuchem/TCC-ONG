package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AgendamentoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints for schedules management")
public class AgendamentoController implements AbstractController<Agendamento, AgendamentoDTO> {

    private final AgendamentoService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<AgendamentoDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                        @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AgendamentoDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public AgendamentoDTO save(@RequestBody @Valid Agendamento agendamento) {
        return service.save(agendamento);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<AgendamentoDTO> search(@RequestParam String value,
                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                       @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public AgendamentoDTO update(@PathVariable UUID id, @RequestBody @Valid Agendamento agendamento) {

        if (agendamento.getId().equals(id)) {
            return service.save(agendamento);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}
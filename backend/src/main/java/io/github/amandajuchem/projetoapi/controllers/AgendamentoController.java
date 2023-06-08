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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamento", description = "Endpoints para gerenciamento de agendamentos")
public class AgendamentoController implements AbstractController<Agendamento, AgendamentoDTO> {

    private final AgendamentoService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AgendamentoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var agendamentos = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> findById(@PathVariable UUID id) {
        final var agendamento = service.findById(id);
        return ResponseEntity.status(OK).body(agendamento);
    }

    @Override
    @PostMapping
    public ResponseEntity<AgendamentoDTO> save(@RequestBody @Valid Agendamento agendamento) {
        final var agendamentoSaved = service.save(agendamento);
        return ResponseEntity.status(CREATED).body(agendamentoSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AgendamentoDTO>> search(@RequestParam String value,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var agendamentos = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> update(@PathVariable UUID id, @RequestBody @Valid Agendamento agendamento) {

        if (agendamento.getId().equals(id)) {
            final var agendamentoSaved = service.save(agendamento);
            return ResponseEntity.status(OK).body(agendamentoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}
package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Usuario controller.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final FacadeService facade;

    /**
     * Instantiates a new Usuario controller.
     *
     * @param facade the facade
     */
    @Autowired
    public UsuarioController(FacadeService facade) {
        this.facade = facade;
    }
}
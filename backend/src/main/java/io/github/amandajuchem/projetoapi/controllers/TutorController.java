package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Tutor controller.
 */
@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final FacadeService facade;

    /**
     * Instantiates a new Tutor controller.
     *
     * @param facade the facade
     */
    @Autowired
    public TutorController(FacadeService facade) {
        this.facade = facade;
    }
}
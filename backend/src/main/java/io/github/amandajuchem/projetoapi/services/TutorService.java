package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Tutor service.
 */
@Service
public class TutorService {

    private final TutorRepository repository;

    /**
     * Instantiates a new Tutor service.
     *
     * @param repository the repository
     */
    @Autowired
    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }


}
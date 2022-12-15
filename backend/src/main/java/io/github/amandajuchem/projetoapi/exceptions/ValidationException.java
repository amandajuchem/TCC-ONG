package io.github.amandajuchem.projetoapi.exceptions;

/**
 * The type Validation exception.
 *
 * @author Edson Isaac
 */
public class ValidationException extends RuntimeException {

    /**
     * Instantiates a new Validation exception.
     *
     * @param message the message
     */
    public ValidationException(String message) {
        super(message);
    }
}
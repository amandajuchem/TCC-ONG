package io.github.amandajuchem.projetoapi.exceptions;

/**
 * The type Object not found exception.
 *
 * @author Edson Isaac
 */
public class ObjectNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Object not found exception.
     *
     * @param message the message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
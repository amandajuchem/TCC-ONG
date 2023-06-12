package io.github.amandajuchem.projetoapi.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    /**
     * Constructs a new ObjectNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
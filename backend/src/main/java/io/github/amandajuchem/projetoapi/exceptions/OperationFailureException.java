package io.github.amandajuchem.projetoapi.exceptions;

public class OperationFailureException extends RuntimeException {

    /**
     * Constructs a new OperationFailureException with the specified detail message.
     *
     * @param message the detail message
     */
    public OperationFailureException(String message) {
        super(message);
    }
}
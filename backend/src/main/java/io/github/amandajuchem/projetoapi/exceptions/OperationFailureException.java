package io.github.amandajuchem.projetoapi.exceptions;

/**
 * The type Operation failure exception.
 */
public class OperationFailureException extends RuntimeException {

    /**
     * Instantiates a new Operation failure exception.
     *
     * @param message the message
     */
    public OperationFailureException(String message) {
        super(message);
    }
}
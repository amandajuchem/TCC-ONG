package io.github.amandajuchem.projetoapi.exceptions;

public class OperationFailureException extends RuntimeException {

    public OperationFailureException(String message) {
        super(message);
    }
}
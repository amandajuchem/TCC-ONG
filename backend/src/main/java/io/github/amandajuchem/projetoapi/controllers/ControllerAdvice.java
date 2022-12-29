package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.StandardError;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Controller advice.
 *
 * @author Edson Isaac
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Controller advice.
     *
     * @param messageSource the message source
     */
    @Autowired
    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Method argument not valid exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        var errors = ex.getBindingResult().getFieldErrors().stream().map(error ->
                StandardError.builder()
                        .timestamp(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(StringUtils.capitalize(error.getField()) + " " + error.getDefaultMessage() + "!")
                        .path(request.getRequestURI())
                        .build()
        ).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Object not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {

        var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(error));
    }

    @ExceptionHandler(OperationFailureException.class)
    public ResponseEntity operationFailureException(OperationFailureException ex, HttpServletRequest request) {

        var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(error));
    }

    /**
     * Validation exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationException(ValidationException ex, HttpServletRequest request) {

        var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(error));
    }
}
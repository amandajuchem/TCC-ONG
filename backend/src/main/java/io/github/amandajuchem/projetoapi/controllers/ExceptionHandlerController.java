package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.StandardError;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * ExceptionHandlerController class for handling exceptions in the API.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Handles AuthenticationException and returns an Unauthorized response.
     *
     * @param ex      The AuthenticationException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a StandardError object representing the error response with HTTP status 401 (UNAUTHORIZED).
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<StandardError> authenticationException(AuthenticationException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(UNAUTHORIZED.value())
                .error("Unauthorized")
                .message(MessageUtils.AUTHENTICATION_FAIL)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, UNAUTHORIZED);
    }

    /**
     * Handles FileNotFoundException and returns a Not Found response.
     *
     * @param ex      The FileNotFoundException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a StandardError object representing the error response with HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<StandardError> fileNotFoundException(FileNotFoundException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a Bad Request response.
     *
     * @param ex      The MethodArgumentNotValidException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a list of StandardError objects representing the error responses with HTTP status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<List<StandardError>> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        final var errors = ex.getBindingResult().getFieldErrors().stream().map(error ->
                StandardError.builder()
                        .timestamp(System.currentTimeMillis())
                        .status(BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(StringUtils.capitalize(error.getField()) + " " + error.getDefaultMessage() + "!")
                        .path(request.getRequestURI())
                        .build()
        ).toList();

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    /**
     * Handles ObjectNotFoundException and returns a Not Found response.
     *
     * @param ex      The ObjectNotFoundException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a StandardError object representing the error response with HTTP status 404 (NOT_FOUND).
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    /**
     * Handles OperationFailureException and returns an Internal Server Error response.
     *
     * @param ex      The OperationFailureException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a StandardError object representing the error response with HTTP status 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(OperationFailureException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> operationFailureException(OperationFailureException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles ValidationException and returns a Bad Request response.
     *
     * @param ex      The ValidationException thrown.
     * @param request The HttpServletRequest object corresponding to the current request.
     * @return A ResponseEntity containing a StandardError object representing the error response with HTTP status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<StandardError> validationException(ValidationException ex, HttpServletRequest request) {

        final var error = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}
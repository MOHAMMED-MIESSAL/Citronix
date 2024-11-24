package com.brief.citronix.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler pour l'exception EntityNotFoundException.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handler pour l'exception ValidationException.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler pour les erreurs de validation des DTO (MethodArgumentNotValidException).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Récupérer uniquement les messages d'erreur
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage) // Obtenir le message
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    /**
     * Handler générique pour toute autre exception.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Une erreur interne est survenue : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler pour l'exception CustomValidationException.
     */
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<String> handleCustomValidationException(CustomValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler pour l'exception HttpMessageNotReadableException.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid input: a numeric value is required, but a string was provided.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}

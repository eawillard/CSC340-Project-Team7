package LibraryPal.demo.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    // Handle validation errors in request bodies
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidBody(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return error(HttpStatus.BAD_REQUEST, message);
    }

    // Handle validation errors on individual parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(
            ConstraintViolationException exception) {

        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    // Handle database constraint conflicts, such as duplicate emails
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleConflict(
            DataIntegrityViolationException exception) {

        return error(
                HttpStatus.CONFLICT,
                "The request conflicts with an existing record."
        );
    }

    // Handle HTTP errors raised by services
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleStatusException(
            ResponseStatusException exception) {

        HttpStatus status = HttpStatus.valueOf(
                exception.getStatusCode().value()
        );

        String message = exception.getReason() != null
                ? exception.getReason()
                : "The request could not be completed.";

        return error(status, message);
    }

    // Create a consistent JSON error response
    private ResponseEntity<Map<String, String>> error(
            HttpStatus status, String message) {

        return ResponseEntity.status(status)
                .body(Map.of("error", message));
    }
}
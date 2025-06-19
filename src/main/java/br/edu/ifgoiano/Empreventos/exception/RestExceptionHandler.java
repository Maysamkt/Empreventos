package br.edu.ifgoiano.Empreventos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<StandardError> handleNotFoundException(NoSuchElementException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Data Integrity Violation",
                "Não foi possível processar a requisição. Causa provável: violação de constraint (ex: email/CPF já cadastrado).",
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Validation Error",
                "Um ou mais campos são inválidos.",
                request.getRequestURI(),
                validationErrors);

        return new ResponseEntity<>(error, status);
    }

    // Handler para argumentos ilegais, que estava no UserExceptionHandler
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<StandardError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Illegal Argument",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleGeneralExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado no servidor: " + ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(error, status);
    }
}
package br.edu.infnet.davifelicianoapi.controller.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.infnet.davifelicianoapi.model.exceptions.AnoInvalidoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInvalidoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoJaPagoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.DataInvalidaException;
import br.edu.infnet.davifelicianoapi.model.exceptions.EncargoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.EncargoInvalidoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.PagamentoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.PagamentoInvalidoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Exceções de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(DataInvalidaException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Exceções de negócio
    @ExceptionHandler(BoletoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleBoletoInvalidoException(BoletoInvalidoException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoletoInexistenteException.class)
    public ResponseEntity<Map<String, String>> handleBoletoInexistenteException(BoletoInexistenteException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.NOT_FOUND.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BoletoJaPagoException.class)
    public ResponseEntity<Map<String, String>> handleBoletoJaPagoException(BoletoJaPagoException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.CONFLICT.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handlePagamentoInvalidoException(PagamentoInvalidoException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PagamentoInexistenteException.class)
    public ResponseEntity<Map<String, String>> handlePagamentoInexistenteException(PagamentoInexistenteException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.NOT_FOUND.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EncargoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleEncargoInvalidoException(EncargoInvalidoException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EncargoInexistenteException.class)
    public ResponseEntity<Map<String, String>> handleEncargoInexistenteException(EncargoInexistenteException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.NOT_FOUND.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleAnoInvalidoException(AnoInvalidoException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Tratamento geral
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put("message", "Violação de integridade de dados: provavelmente chave duplicada.");

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("time", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errors.put("message", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

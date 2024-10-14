package com.dev.accountservice.exceptions;

import com.dev.accountservice.domain.core.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDto> runtimeExceptionHandler(RuntimeException ex) {
        ErrorDto errorDto = ErrorDto.builder().code("500").message(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorDto>> handleValidationExceptions(Exception ex) {
        List<FieldError> fieldErrors;

        // Manejo correcto para cada tipo de excepción
        if (ex instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        } else {
            // Para ValidationException, retornamos un error genérico o personalizado.
            ErrorDto error = ErrorDto.builder().code("400").message("Validation error").build();
            return ResponseEntity.badRequest().body(List.of(error));
        }

        // Procesamos los FieldErrors si proviene de MethodArgumentNotValidException
        List<ErrorDto> errors = fieldErrors.stream()
                .map(error -> ErrorDto.builder()
                        .code("400")
                        .message(error.getField() + ": " + error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }
}


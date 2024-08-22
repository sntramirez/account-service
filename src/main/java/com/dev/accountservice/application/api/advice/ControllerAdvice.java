package com.dev.accountservice.application.api.advice;

import com.dev.accountservice.domain.core.model.ErrorDto;
import com.dev.accountservice.domain.core.model.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        if (ex instanceof ValidationException) {
            fieldErrors = ((ValidationException) ex).getBindingResult().getFieldErrors();
        } else {
            fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        }

        List<ErrorDto> errors = fieldErrors.stream()
                .map(error -> ErrorDto.builder()
                        .code("400")
                        .message(error.getField() + ": " + error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

}

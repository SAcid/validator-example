package com.example.validatorexample.excption;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ExceptionResponse> handleConstraintViolationException(
      ConstraintViolationException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse.builder().message(e.getLocalizedMessage()).build());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    return ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(error -> ((FieldError) error).getField(),
        DefaultMessageSourceResolvable::getDefaultMessage));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException e) {
    log.error(e.getMessage(), e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse.builder()
            .message(e.getMessage()).build());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
    log.error(e.getMessage(), e);

    final BindingResult bindingResult = e.getBindingResult();
    final FieldError fieldError = e.getBindingResult().getFieldError();
    String errorMessage =
        Objects.isNull(fieldError) ? e.getMessage() : fieldError.getDefaultMessage();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse.builder()
            .message(errorMessage)
            .errors(bindingResult.getAllErrors())
            .build());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse.builder()
            .message(e.getMessage()).build());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ExceptionResponse> httpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ExceptionResponse.builder().message(e.getMessage()).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.builder().message(e.getMessage()).build());
  }

  @Builder
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter
  @ToString
  public static class ExceptionResponse {
    private final String message;
    private final List<ObjectError> errors;
  }
}
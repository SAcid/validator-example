package com.example.validatorexample.datetimerange.controller;

import com.example.validatorexample.datetimerange.request.Event;
import com.example.validatorexample.datetimerange.request.Period;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DateTimeRageController {
  @PostMapping(value = "/api/period", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public Period period(@Valid @RequestBody Period period) {
    log.info("test1: period = {}", period);
    return period;
  }

  @PostMapping(value = "/api/event", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public Event event(@Valid @RequestBody Event event) {
    log.info("test1: event = {}", event);
    return event;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidaExceptionHandler(MethodArgumentNotValidException e) {
    final BindingResult bindingResult = e.getBindingResult();

    Optional<String> message = bindingResult.getAllErrors().stream()
        .map(ObjectError::getDefaultMessage)
        .filter(Objects::nonNull)
        .findFirst();

    return ResponseEntity.badRequest()
        .body(message.orElse(e.getMessage()));
  }
}

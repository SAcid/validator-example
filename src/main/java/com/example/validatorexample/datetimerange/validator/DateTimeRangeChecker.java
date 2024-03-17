package com.example.validatorexample.datetimerange.validator;

import java.time.LocalDateTime;

public interface DateTimeRangeChecker {
  LocalDateTime getStartAt();
  LocalDateTime getEndAt();
  default String toDateTimeParseErrorString() {
    return "datetime parse Error - %s".formatted(this);
  }

}

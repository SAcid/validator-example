package com.example.validatorexample.datetimerange.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeRangeCheckValidator implements ConstraintValidator<DateTimeRangeCheck, DateTimeRangeChecker> {

  private boolean equals;
  private boolean nullable;

  @Override
  public void initialize(DateTimeRangeCheck constraintAnnotation) {
    equals = constraintAnnotation.equals();
    nullable = constraintAnnotation.nullable();
  }

  @Override
  public boolean isValid(DateTimeRangeChecker checker,
      ConstraintValidatorContext constraintValidatorContext) {
    try {
      LocalDateTime start = checker.getStartAt();
      LocalDateTime end = checker.getEndAt();

      if (Objects.isNull(start) || Objects.isNull(end)) {
        return nullable;
      }

      return equals ? !end.isBefore(start)
          : start.isBefore(end);
    } catch (
        DateTimeParseException e) {
      log.warn(checker.toDateTimeParseErrorString());
      return false;
    }
  }
}

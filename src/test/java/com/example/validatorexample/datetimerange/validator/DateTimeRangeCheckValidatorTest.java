package com.example.validatorexample.datetimerange.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeRangeCheckValidatorTest {

  @Getter
  @DateTimeRangeCheck
  @RequiredArgsConstructor
  static class DefaultChecker implements DateTimeRangeChecker {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
  }

  @DisplayName("nullable: false, equals: false - 시작일시와 종료일시가 같음")
  @Test
  public void test1() {
    final DateTimeRangeCheckValidator validator = new DateTimeRangeCheckValidator();
    final var start = LocalDateTime.now();
    final var end = LocalDateTime.from(start);
    final DateTimeRangeChecker data = new DefaultChecker(start, end);

    validator.initialize(DefaultChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isFalse();
  }


  @DisplayName("nullable: false, equals: false - 시작일시가 종료일시보다 빠름")
  @Test
  public void test2() {
    final DateTimeRangeCheckValidator validator = new DateTimeRangeCheckValidator();
    LocalDateTime end = LocalDateTime.now();
    LocalDateTime start = end.minus(1, ChronoUnit.NANOS);
    final DateTimeRangeChecker data = new DefaultChecker(start, end);

    validator.initialize(DefaultChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isTrue();
  }

  @DisplayName("nullable: false, equals: false - 종료일시가 시작일시보다 빠름")
  @Test
  public void test3() {
    var validator = new DateTimeRangeCheckValidator();
    var start = LocalDateTime.now();
    var end = start.minus(1, ChronoUnit.NANOS);
    var data = new DefaultChecker(start, end);

    validator.initialize(DefaultChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isFalse();
  }

  @Getter
  @DateTimeRangeCheck(nullable = true)
  @RequiredArgsConstructor
  static class NullableTrue implements DateTimeRangeChecker {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
  }

  @DisplayName("nullable: true - 시작일시: NULL")
  @Test
  public void test4() {

    final DateTimeRangeCheckValidator validator = new DateTimeRangeCheckValidator();
    final DateTimeRangeChecker data = new NullableTrue(
        null, LocalDateTime.now());

    validator.initialize(NullableTrue.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isTrue();
  }

  @DisplayName("nullable: true - 종료일시: NULL")
  @Test
  public void test5() {

    final DateTimeRangeCheckValidator validator = new DateTimeRangeCheckValidator();
    final NullableTrue data = new NullableTrue(
        LocalDateTime.now(), null);

    validator.initialize(NullableTrue.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isTrue();
  }


  @Getter
  @DateTimeRangeCheck(equals = true)
  @RequiredArgsConstructor
  static class EqualsChecker implements DateTimeRangeChecker {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
  }


  @DisplayName("nullable: false, equals: true - 시작일시와 종료일시가 같음")
  @Test
  public void test6() {
    final var validator = new DateTimeRangeCheckValidator();
    final var start = LocalDateTime.now();
    final var end = LocalDateTime.from(start);
    final var data = new EqualsChecker(start, end);

    validator.initialize(EqualsChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isTrue();
  }


  @DisplayName("nullable: false, equals: true - 시작일시가 종료일시보다 빠름")
  @Test
  public void test7() {
    final var validator = new DateTimeRangeCheckValidator();
    final var end = LocalDateTime.now();
    final var start = end.minus(1, ChronoUnit.NANOS);
    final var data = new EqualsChecker(start, end);

    validator.initialize(EqualsChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isTrue();
  }

  @DisplayName("nullable: false, equals: true - 종료일시가 시작일시보다 빠름")
  @Test
  public void test8() {
    var validator = new DateTimeRangeCheckValidator();
    var start = LocalDateTime.now();
    var end = start.minus(1, ChronoUnit.NANOS);
    var data = new EqualsChecker(start, end);

    validator.initialize(EqualsChecker.class.getAnnotation(
        DateTimeRangeCheck.class));
    assertThat(validator.isValid(data, null)).isFalse();
  }
}
package com.example.validatorexample.datetimerange.request;

import com.example.validatorexample.datetimerange.validator.DateTimeRangeCheck;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {
  private final String name;
  @DateTimeRangeCheck(message = "참가일정의 시작일자는 종료일자보다 같거나 빨랴아 햡니다.", equals = true)
  private final DateTimeRange participation;
  @DateTimeRangeCheck(message = "행사일정의 시작일자는 종료일자보다 빨랴아 햡니다.")
  private final DateTimeRange schedule;
}

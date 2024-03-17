package com.example.validatorexample.datetimerange.request;

import com.example.validatorexample.datetimerange.validator.DateTimeRangeChecker;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeRange  implements DateTimeRangeChecker {
  private final LocalDateTime startAt;
  private final LocalDateTime endAt;
}

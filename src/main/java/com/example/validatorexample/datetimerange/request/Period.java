package com.example.validatorexample.datetimerange.request;

import com.example.validatorexample.datetimerange.validator.DateTimeRangeCheck;
import com.example.validatorexample.datetimerange.validator.DateTimeRangeChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@DateTimeRangeCheck(message = "The date range is not valid")
@Getter
@Setter
public class Period implements DateTimeRangeChecker {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime startAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime endAt;
}

package com.example.validatorexample.datetimerange.validator;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, FIELD, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeRangeCheckValidator.class)
public @interface DateTimeRangeCheck {
  String message() default "시작일시는 종료일시보다 빨라야 합니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  /**
   * 시작일시, 종료일시에 같을 경우 겅즘
   * @return
   */
  boolean equals() default false;

  /**
   * 시작일시, 종료일시에 null이 있을 경우 검증 
   * @return
   */
  boolean nullable() default false;
}

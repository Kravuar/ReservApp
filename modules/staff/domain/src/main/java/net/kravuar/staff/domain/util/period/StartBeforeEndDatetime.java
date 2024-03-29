package net.kravuar.staff.domain.util.period;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = StartEndDatetimeValidator.class)
@Documented
public @interface StartBeforeEndDatetime {
    String message() default "Start must be before end datetime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

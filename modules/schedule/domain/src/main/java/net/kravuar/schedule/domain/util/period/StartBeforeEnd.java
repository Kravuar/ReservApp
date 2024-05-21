package net.kravuar.schedule.domain.util.period;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {StartEndTimeValidator.class, StartEndDateValidator.class})
@Documented
public @interface StartBeforeEnd {
    String message() default "Start must be before end";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

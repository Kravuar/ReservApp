package net.kravuar.schedule.model.util.period;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {TimePeriodsIntersectionValidator.class, DatePeriodsIntersectionValidator.class})
@Documented
public @interface PeriodsNotIntersect {
    String message() default "Periods should not intersect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package net.kravuar.staff.domain.util.hours;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = WorkingHoursValidator.class)
@Documented
public @interface WorkingHoursNotIntersecting {
    String message() default "Working hours should not intersect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

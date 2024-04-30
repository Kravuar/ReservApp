package net.kravuar.staff.model.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

// TODO: Test this
public class StartEndTimeValidator implements ConstraintValidator<StartBeforeEnd, Period<LocalTime>> {
    @Override
    public boolean isValid(Period<LocalTime> period, ConstraintValidatorContext context) {
        return period.getStart().isBefore(period.getEnd());
    }
}

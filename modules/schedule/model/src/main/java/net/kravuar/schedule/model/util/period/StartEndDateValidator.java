package net.kravuar.schedule.model.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.chrono.ChronoLocalDate;

// TODO: Test this
public class StartEndDateValidator implements ConstraintValidator<StartBeforeEnd, Period<ChronoLocalDate>> {
    @Override
    public boolean isValid(Period<ChronoLocalDate> period, ConstraintValidatorContext context) {
        return period.getStart().isBefore(period.getEnd());
    }
}

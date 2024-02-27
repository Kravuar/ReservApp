package net.kravuar.schedule.domain.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.chrono.ChronoLocalDate;

public class StartEndDateValidator implements ConstraintValidator<StartBeforeEnd, Period<ChronoLocalDate>> {
    @Override
    public boolean isValid(Period<ChronoLocalDate> period, ConstraintValidatorContext context) {
        return period.getStart().isBefore(period.getEnd());
    }
}

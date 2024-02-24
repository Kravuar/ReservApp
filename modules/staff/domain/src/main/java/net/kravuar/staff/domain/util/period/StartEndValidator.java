package net.kravuar.staff.domain.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class StartEndValidator implements ConstraintValidator<StartBeforeEnd, Period> {
    @Override
    public boolean isValid(Period period, ConstraintValidatorContext context) {
        return period.getStart().isBefore(period.getEnd());
    }
}
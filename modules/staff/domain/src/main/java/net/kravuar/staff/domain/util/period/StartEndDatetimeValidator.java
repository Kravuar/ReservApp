package net.kravuar.staff.domain.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class StartEndDatetimeValidator implements ConstraintValidator<StartBeforeEndDatetime, Period> {
    @Override
    public boolean isValid(Period value, ConstraintValidatorContext context) {
        return value.getStart().isBefore(value.getEnd());
    }
}
package net.kravuar.schedule.domain.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class DatePeriodsIntersectionValidator implements ConstraintValidator<StartBeforeEnd, Collection<Period<ChronoLocalDate>>> {
    @Override
    public boolean isValid(Collection<Period<ChronoLocalDate>> periods, ConstraintValidatorContext context) {
        ChronoLocalDate previousEnd = null;
        List<Period<ChronoLocalDate>> sorted = periods.stream()
                .sorted(Comparator.comparing(Period::getStart))
                .toList();
        for (Period<ChronoLocalDate> fragment: sorted) {
            if (previousEnd != null && previousEnd.isAfter(fragment.getStart()))
                return false;

            previousEnd = fragment.getEnd();
        }
        return true;
    }
}

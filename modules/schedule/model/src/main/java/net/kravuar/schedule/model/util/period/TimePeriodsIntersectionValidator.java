package net.kravuar.schedule.model.util.period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// TODO: Test this
public class TimePeriodsIntersectionValidator implements ConstraintValidator<PeriodsNotIntersect, Collection<Period<LocalTime>>> {
    @Override
    public boolean isValid(Collection<Period<LocalTime>> periods, ConstraintValidatorContext context) {
        LocalTime previousEnd = null;
        List<Period<LocalTime>> sorted = periods.stream()
                .sorted(Comparator.comparing(Period::getStart))
                .toList();
        for (Period<LocalTime> fragment : sorted) {
            if (previousEnd != null && previousEnd.isAfter(fragment.getStart()))
                return false;

            previousEnd = fragment.getEnd();
        }
        return true;
    }
}

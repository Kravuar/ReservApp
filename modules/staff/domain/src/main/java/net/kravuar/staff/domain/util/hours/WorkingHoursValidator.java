package net.kravuar.staff.domain.util.hours;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.domain.WorkingHoursFragment;

import java.time.LocalTime;

class WorkingHoursValidator implements ConstraintValidator<WorkingHoursNotIntersecting, DailySchedule> {
    @Override
    public boolean isValid(DailySchedule schedule, ConstraintValidatorContext context) {
        LocalTime previousEnd = null;
        for (WorkingHoursFragment fragment: schedule.getWorkingHours()) {
            if (previousEnd != null && previousEnd.isAfter(fragment.getStart()))
                return false;

            previousEnd = fragment.getEnd();
        }
        return true;
    }
}
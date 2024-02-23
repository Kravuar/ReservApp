package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import net.kravuar.staff.domain.exceptions.InvalidScheduleException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NavigableSet;
import java.util.TreeSet;

@Getter
public class DailySchedule {
    private final DayOfWeek dayOfWeek;
    private final LocalDateTime validFrom;
    private final NavigableSet<WorkingHoursFragment> workingHours;

    public DailySchedule(DayOfWeek dayOfWeek) {
        this(dayOfWeek, LocalDateTime.now(), new TreeSet<>());
    }

    public DailySchedule(DayOfWeek dayOfWeek, LocalDateTime validFrom) {
        this(dayOfWeek, validFrom, new TreeSet<>());
    }

    public DailySchedule(DayOfWeek dayOfWeek, LocalDateTime validFrom, NavigableSet<WorkingHoursFragment> workingHours) {
        validate(workingHours);
        this.dayOfWeek = dayOfWeek;
        this.validFrom = validFrom;
        this.workingHours = new TreeSet<>(workingHours);
    }

    private static void validate(NavigableSet<WorkingHoursFragment> workingHours) throws InvalidScheduleException {
        LocalTime previousEnd = null;
        for (WorkingHoursFragment fragment : workingHours) {
            if (previousEnd != null && previousEnd.isAfter(fragment.getStart()))
                throw new InvalidScheduleException();

            previousEnd = fragment.getEnd();
        }
    }

}

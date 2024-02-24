package net.kravuar.staff.domain.commands;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NavigableSet;

@Data
public class ChangeDailyScheduleCommand {
    private final long id;
    private final long serviceId;
    private final long staffId;
    private final DayOfWeek dayOfWeek;
    private final LocalDate validFrom = LocalDate.now();
    private final NavigableSet<WorkingHourDTO> workingHours;
    private final LocalDate validUntil; // Null means unlimited

    public record WorkingHourDTO(
        LocalTime start,
        LocalTime end,
        double cost
    ) {}
}
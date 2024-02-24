package net.kravuar.staff.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Builder
public class StaffSchedule {
    private final Long id;
    @NotNull
    private final Service service;
    @NotNull
    private final Staff staff;
    @Builder.Default
    private final Map<DayOfWeek, @NotNull DailySchedule> schedule = new EnumMap<>(DayOfWeek.class);
}

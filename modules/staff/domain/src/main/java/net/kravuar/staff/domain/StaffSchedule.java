package net.kravuar.staff.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

@Setter
@Getter
@Builder
public class StaffSchedule {
    private final Long id;
    private final Service service;
    private final Staff staff;
    @Builder.Default
    private final Map<DayOfWeek, DailySchedule> schedule = new EnumMap<>(DayOfWeek.class);
}

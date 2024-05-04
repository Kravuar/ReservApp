package net.kravuar.schedule.dto;

import java.util.List;

public record ScheduleOfStaffDTO(
        StaffDTO staff,
        List<ScheduleOfDayDTO> schedule
) {
}

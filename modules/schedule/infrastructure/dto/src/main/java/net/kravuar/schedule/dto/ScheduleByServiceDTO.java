package net.kravuar.schedule.dto;

import java.util.List;

public record ScheduleByServiceDTO(
        StaffDTO staff,
        List<ScheduleOfDayDTO> schedule
) {
}

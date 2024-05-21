package net.kravuar.schedule.web;

import net.kravuar.staff.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOServiceMapper.class, DTOStaffMapper.class})
interface DTOScheduleMapper {
    ScheduleDTO scheduleToDTO(Schedule schedule);
}

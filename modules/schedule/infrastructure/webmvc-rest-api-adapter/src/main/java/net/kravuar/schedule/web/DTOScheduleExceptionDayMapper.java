package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.ScheduleExceptionDay;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOServiceMapper.class, DTOStaffMapper.class})
interface DTOScheduleExceptionDayMapper {
    ScheduleExceptionDayDTO scheduleExceptionDayToDTO(ScheduleExceptionDay scheduleExceptionDay);
}

package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.ScheduleExceptionDay;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOServiceMapper.class, DTOStaffMapper.class})
public interface DTOScheduleExceptionDayMapper {
    ScheduleExceptionDayDTO scheduleExceptionDayToDTO(ScheduleExceptionDay scheduleExceptionDay);
}

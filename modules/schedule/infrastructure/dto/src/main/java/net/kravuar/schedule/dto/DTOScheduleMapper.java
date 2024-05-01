package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOServiceMapper.class, DTOStaffMapper.class})
public interface DTOScheduleMapper {
    ScheduleDTO scheduleToDTO(Schedule schedule);
}

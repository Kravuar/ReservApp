package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.Staff;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOBusinessMapper.class})
public interface DTOStaffMapper {
    StaffDTO staffToDTO(Staff staff);
}

package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.Staff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOStaffMapper {
    StaffDTO staffToDTO(Staff staff);
}

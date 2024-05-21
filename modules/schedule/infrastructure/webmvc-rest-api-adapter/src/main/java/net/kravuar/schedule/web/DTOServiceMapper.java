package net.kravuar.schedule.web;

import net.kravuar.staff.model.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOServiceMapper {
    ServiceDTO staffToDTO(Service service);
}

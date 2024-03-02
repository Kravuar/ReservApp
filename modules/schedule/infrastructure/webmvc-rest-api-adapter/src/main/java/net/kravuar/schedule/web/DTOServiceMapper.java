package net.kravuar.schedule.web;

import net.kravuar.schedule.domain.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOServiceMapper {
    ServiceDTO staffToDTO(Service service);
}

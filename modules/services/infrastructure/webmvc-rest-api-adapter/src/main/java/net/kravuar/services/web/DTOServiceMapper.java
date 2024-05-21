package net.kravuar.services.web;

import net.kravuar.services.domain.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOServiceMapper {
    ServiceDTO toDTO(Service service);
}

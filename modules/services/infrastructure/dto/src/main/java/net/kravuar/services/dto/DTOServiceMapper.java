package net.kravuar.services.dto;

import net.kravuar.services.model.Service;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOBusinessMapper.class})
public interface DTOServiceMapper {
    ServiceDTO toDTO(Service service);
}

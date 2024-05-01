package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.Service;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOBusinessMapper.class})
public interface DTOServiceMapper {
    ServiceDTO staffToDTO(Service service);
}

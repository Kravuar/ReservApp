package net.kravuar.schedule.dto;

import net.kravuar.staff.model.Business;
import org.mapstruct.Mapper;

@Mapper
public interface DTOBusinessMapper {
    BusinessDTO toDTO(Business service);
}

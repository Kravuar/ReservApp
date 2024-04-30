package net.kravuar.services.dto;

import net.kravuar.services.model.Business;
import org.mapstruct.Mapper;

@Mapper
public interface DTOBusinessMapper {
    BusinessDTO toDTO(Business business);
}

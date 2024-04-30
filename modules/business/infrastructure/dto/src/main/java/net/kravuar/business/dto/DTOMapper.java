package net.kravuar.business.dto;

import net.kravuar.business.model.Business;
import org.mapstruct.Mapper;

@Mapper
public interface DTOMapper {
    BusinessDTO toDTO(Business business);
}

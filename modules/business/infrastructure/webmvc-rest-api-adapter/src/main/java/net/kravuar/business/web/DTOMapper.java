package net.kravuar.business.web;

import net.kravuar.business.domain.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DTOMapper {
    BusinessDTO toDTO(Business business);
}

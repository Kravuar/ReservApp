package net.kravuar.services.web;

import net.kravuar.services.model.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DTOBusinessMapper {
    BusinessDTO toDTO(Business business);
}

package net.kravuar.staff.web;

import net.kravuar.staff.domain.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DTOBusinessMapper {
    BusinessDTO toDTO(Business service);
}

package net.kravuar.schedule.web;

import net.kravuar.staff.model.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DTOBusinessMapper {
    BusinessDTO toDTO(Business service);
}

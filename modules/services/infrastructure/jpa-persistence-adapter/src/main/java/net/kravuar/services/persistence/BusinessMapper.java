package net.kravuar.services.persistence;

import net.kravuar.services.domain.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface BusinessMapper {
    BusinessModel toModel(Business business);

    Business toDomain(BusinessModel businessModel);
}

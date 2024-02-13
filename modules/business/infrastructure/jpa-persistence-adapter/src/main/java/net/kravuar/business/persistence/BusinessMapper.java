package net.kravuar.business.persistence;

import net.kravuar.business.domain.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface BusinessMapper {
    BusinessModel toModel(Business business);
    Business toDomain(BusinessModel businessModel);
}

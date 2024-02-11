package net.kravuar.business.persistence;

import net.kravuar.business.domain.Business;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface BusinessMapper {
    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    BusinessModel toModel(Business business);
    Business toDomain(BusinessModel businessModel);
}

package net.kravuar.business.model.mappers;

import net.kravuar.business.domain.Business;
import net.kravuar.business.model.BusinessModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    BusinessModel toModel(Business business);
    Business toDomain(BusinessModel businessModel);
}

package net.kravuar.services.persistence;

import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
abstract class ServiceMapper {
    protected BusinessRepository businessRepository;

    abstract Service toDomain(ServiceModel servicesModel);

    @Mapping(target = "business", source = "business", qualifiedByName = "toReference")
    abstract ServiceModel toModel(Service service);

    @Named("toReference")
    protected BusinessModel toReference(Business business) {
        return businessRepository.getReferenceById(business.getId());
    }
}

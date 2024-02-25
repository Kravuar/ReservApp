package net.kravuar.staff.persistence.schedule;

import net.kravuar.staff.domain.WorkingHoursFragment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface WorkingHoursMapper {
    WorkingHoursFragment toDomain(WorkingHoursFragmentEmbeddable embeddable);
    WorkingHoursFragmentEmbeddable toModel(WorkingHoursFragment domain);
}

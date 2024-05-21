package net.kravuar.staff.web;

import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOStaffMapper {
    StaffInvitationDTO invitationToDTO(StaffInvitation invitation);

    StaffDTO staffToDTO(StaffDetailed staff);
}

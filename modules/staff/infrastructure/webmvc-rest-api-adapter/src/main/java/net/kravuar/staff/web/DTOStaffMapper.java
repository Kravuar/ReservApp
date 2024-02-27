package net.kravuar.staff.web;

import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.StaffInvitation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOBusinessMapper.class})
interface DTOStaffMapper {
    StaffInvitationDTO invitationToDTO(StaffInvitation invitation);

    StaffDTO staffToDTO(Staff invitation);
}

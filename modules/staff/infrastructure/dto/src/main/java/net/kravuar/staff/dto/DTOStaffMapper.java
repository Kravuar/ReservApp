package net.kravuar.staff.dto;

import net.kravuar.staff.model.StaffDetailed;
import net.kravuar.staff.model.StaffInvitation;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOBusinessMapper.class})
public interface DTOStaffMapper {
    StaffInvitationDTO invitationToDTO(StaffInvitation invitation);

    StaffDTO staffToDTO(StaffDetailed staff);
}

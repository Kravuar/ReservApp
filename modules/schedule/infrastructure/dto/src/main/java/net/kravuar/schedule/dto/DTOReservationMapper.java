package net.kravuar.schedule.dto;

import net.kravuar.staff.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOStaffMapper.class, DTOServiceMapper.class})
public interface DTOReservationMapper {
    ReservationDTO reservationToDTO(Reservation reservation);
    AnonymousReservationDTO reservationToAnonymousDTO(Reservation reservation);
}

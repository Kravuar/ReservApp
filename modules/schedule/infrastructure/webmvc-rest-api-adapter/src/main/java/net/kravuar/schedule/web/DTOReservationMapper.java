package net.kravuar.schedule.web;

import net.kravuar.staff.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DTOStaffMapper.class, DTOServiceMapper.class})
interface DTOReservationMapper {
    ReservationDTO reservationToDTO(Reservation reservation);
    AnonymousReservationDTO reservationToAnonymousDTO(Reservation reservation);
}

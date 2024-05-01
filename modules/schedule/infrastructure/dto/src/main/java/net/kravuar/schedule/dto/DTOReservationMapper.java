package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(uses = {DTOStaffMapper.class, DTOServiceMapper.class})
public interface DTOReservationMapper {
    ReservationDetailedDTO reservationToDTO(Reservation reservation);

    ReservationDTO reservationToAnonymousDTO(Reservation reservation);
}

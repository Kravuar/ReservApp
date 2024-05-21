package net.kravuar.schedule.dto;

import net.kravuar.schedule.model.Schedule;
import net.kravuar.schedule.model.weak.ReservationSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(uses = {DTOServiceMapper.class, DTOStaffMapper.class})
public interface DTOScheduleMapper {
    ScheduleDTO scheduleToDTO(Schedule schedule);

    @Mapping(source = "slot.start", target = "start")
    @Mapping(source = "slot.end", target = "end")
    @Mapping(source = "slot.cost", target = "cost")
    @Mapping(source = "slot.maxReservations", target = "maxReservations")
    ReservationSlotDTO slotToDTO(ReservationSlot slot, LocalDate date, ServiceDTO service, StaffDTO staff);
}

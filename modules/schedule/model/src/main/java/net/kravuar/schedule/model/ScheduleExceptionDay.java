package net.kravuar.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kravuar.schedule.model.weak.ReservationSlot;

import java.time.LocalDate;
import java.util.SortedSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleExceptionDay {
    private Long id;
    private LocalDate date;
    private Staff staff;
    private Service service;
    private SortedSet<ReservationSlot> reservationSlots;
}

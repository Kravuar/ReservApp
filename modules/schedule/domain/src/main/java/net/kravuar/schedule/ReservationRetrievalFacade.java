package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import net.kravuar.schedule.ports.out.ReservationRetrievalPort;

import java.time.LocalDate;
import java.util.List;
import java.util.NavigableMap;

@AppComponent
@RequiredArgsConstructor
public class ReservationRetrievalFacade implements ReservationRetrievalUseCase {
    private final ReservationRetrievalPort reservationRetrievalPort;

    @Override
    public Reservation findById(long reservationId) {
        return reservationRetrievalPort.findActiveById(reservationId);
    }

    @Override
    public NavigableMap<LocalDate, List<Reservation>> findAllByStaff(long staffId, LocalDate from, LocalDate to) {
        return reservationRetrievalPort.findAllActiveByStaff(staffId, from, to, true);
    }

    @Override
    public NavigableMap<LocalDate, List<Reservation>> findAllByClient(String clientSub, LocalDate from, LocalDate to) {
        return reservationRetrievalPort.findAllActiveByClient(clientSub, from, to);
    }
}

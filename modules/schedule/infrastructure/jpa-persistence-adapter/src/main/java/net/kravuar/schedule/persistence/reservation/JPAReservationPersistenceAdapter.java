package net.kravuar.schedule.persistence.reservation;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.ports.out.ReservationPersistencePort;
import net.kravuar.staff.model.Reservation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JPAReservationPersistenceAdapter implements ReservationPersistencePort {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}

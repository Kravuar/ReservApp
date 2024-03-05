package net.kravuar.schedule.persistence.reservation;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.ports.out.ReservationPersistencePort;
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

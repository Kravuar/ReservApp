package net.kravuar.schedule.persistence.reservation;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.exceptions.ReservationNotFoundException;
import net.kravuar.schedule.model.Reservation;
import net.kravuar.schedule.ports.out.ReservationRetrievalPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JPAReservationRetrievalAdapter implements ReservationRetrievalPort {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation findActiveById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);
    }

    @Override
    public NavigableMap<LocalDate, List<Reservation>> findAllActiveByStaff(long staffId, LocalDate from, LocalDate to, boolean fullyActiveOnly) {
        List<Reservation> reservations = fullyActiveOnly
                ? reservationRepository.findAllFullyActiveByStaff(staffId, from, to)
                : reservationRepository.findAllActiveByStaff(staffId, from, to);
        return reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getDate,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public NavigableMap<LocalDate, List<Reservation>> findAllActiveByStaff(String sub, LocalDate from, LocalDate to, boolean fullyActiveOnly) {
        List<Reservation> reservations = fullyActiveOnly
                ? reservationRepository.findAllFullyActiveByStaff(sub, from, to)
                : reservationRepository.findAllActiveByStaff(sub, from, to);
        return reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getDate,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public NavigableMap<LocalDate, List<Reservation>> findAllActiveByClient(String clientSub, LocalDate from, LocalDate to) {
        return reservationRepository.findAllActiveBySub(clientSub, from, to).stream()
                .collect(Collectors.groupingBy(
                        Reservation::getDate,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public SortedMap<LocalDate, List<Reservation>> findAllByService(long serviceId, LocalDate from, LocalDate to) {
        return reservationRepository.findAllActiveByService(serviceId, from, to).stream()
                .collect(Collectors.groupingBy(
                        Reservation::getDate,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public List<Reservation> findAllReservationsBySlot(LocalDate date, LocalTime start, long serviceId, long staffId) {
        return reservationRepository.findAllByDateAndStartAndServiceIdAndStaffId(date, start, serviceId, staffId);
    }
}

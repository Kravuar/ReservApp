package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.dto.AnonymousReservationsByDayDTO;
import net.kravuar.schedule.dto.DTOReservationMapper;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationsByDayDTO;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation/retrieval")
@RequiredArgsConstructor
class ReservationRetrievalController {
    private final ReservationRetrievalUseCase reservationRetrievalUseCase;
    private final DTOReservationMapper dtoReservationMapper;

    @GetMapping("/by-id/{reservationId}")
    @PreAuthorize("hasPermission(#reservationId, 'Reservation', 'Read')")
    ReservationDetailedDTO byId(@PathVariable("reservationId") long reservationId) {
        return dtoReservationMapper.reservationToDTO(
                reservationRetrievalUseCase.findById(reservationId)
        );
    }

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    List<AnonymousReservationsByDayDTO> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(staffId, from, to).entrySet().stream()
                .map(entry -> new AnonymousReservationsByDayDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .filter(reservation -> reservation.getService().getId().equals(serviceId)) // TODO: maybe add another usecase for that
                                .map(dtoReservationMapper::reservationToAnonymousDTO)
                                .toList()
                )).toList();
    }

    @GetMapping("/by-staff/{staffId}/{from}/{to}")
    @PreAuthorize("hasPermission(#staffId, 'ReservationsOfStaff', 'Read')")
    List<ReservationsByDayDTO> byStaff(@PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(staffId, from, to).entrySet().stream()
                .map(entry -> new ReservationsByDayDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                )).toList();
    }

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    List<AnonymousReservationsByDayDTO> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByService(serviceId, from, to).entrySet().stream()
                .map(entry -> new AnonymousReservationsByDayDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToAnonymousDTO)
                                .toList()
                )).toList();
    }

    @GetMapping("/my/{from}/{to}")
    @PreAuthorize("isAuthenticated()")
    List<ReservationsByDayDTO> my(@AuthenticationPrincipal Jwt jwt, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByClient(jwt.getSubject(), from, to).entrySet().stream()
                .map(entry -> new ReservationsByDayDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                )).toList();
    }

    @GetMapping("/to-me/{from}/{to}")
    @PreAuthorize("isAuthenticated()")
    List<ReservationsByDayDTO> toMe(@AuthenticationPrincipal Jwt jwt, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(jwt.getSubject(), from, to).entrySet().stream()
                .map(entry -> new ReservationsByDayDTO(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                )).toList();
    }
}

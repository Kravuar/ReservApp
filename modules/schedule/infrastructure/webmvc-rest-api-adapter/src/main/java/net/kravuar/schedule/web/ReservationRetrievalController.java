package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation/retrieval")
@RequiredArgsConstructor
class ReservationRetrievalController {
    private final ReservationRetrievalUseCase reservationRetrievalUseCase;
    private final DTOReservationMapper dtoReservationMapper;

    @GetMapping("/by-id/{reservationId}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isClientOrStaff(#reservationId, authentication.details.subject)")
    ReservationDTO byId(@PathVariable("reservationId") long reservationId) {
        return dtoReservationMapper.reservationToDTO(
                reservationRetrievalUseCase.findById(reservationId)
        );
    }

    @GetMapping("/by-staff/{staffId}/{from}/{to}")
    @PreAuthorize("isAuthenticated() && @authorizationHandler.isStaff(#staffId, authentication.details.subject)")
    Map<LocalDate, List<ReservationDTO>> byStaff(@PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByStaff(staffId, from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                ));
    }

    @GetMapping("/by-client-sub/{sub}/{from}/{to}")
    @PreAuthorize("isAuthenticated() && #sub.equals(authentication.details.subject)")
    Map<LocalDate, List<ReservationDTO>> byClient(@PathVariable("sub") String sub, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return reservationRetrievalUseCase.findAllByClient(sub, from, to).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dtoReservationMapper::reservationToDTO)
                                .toList()
                ));
    }
}

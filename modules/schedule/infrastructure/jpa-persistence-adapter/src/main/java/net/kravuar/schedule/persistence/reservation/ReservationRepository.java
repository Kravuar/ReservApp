package net.kravuar.schedule.persistence.reservation;

import net.kravuar.schedule.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.sub = :sub " +
            "AND r.date BETWEEN :from AND :to " +
            "AND r.active = :true " +
            "AND r.service.active = true " +
            "AND r.service.business.active = true " +
            "AND r.sub.active = true")
    List<Reservation> findAllActiveBySub(@Param("sub") String sub, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.staff.id = :staffId " +
            "AND r.date BETWEEN :from AND :to " +
            "AND r.active = :true " +
            "AND r.service.active = true " +
            "AND r.service.business.active = true " +
            "AND r.staff.active = true")
    List<Reservation> findAllActiveByStaff(@Param("staffId") long staffId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.staff.id = :staffId " +
            "AND r.date BETWEEN :from AND :to " +
            "AND r.active = :true")
    List<Reservation> findAllByStaff(@Param("staffId") long staffId, @Param("from") LocalDate from, @Param("to") LocalDate to);
}

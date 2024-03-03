package net.kravuar.schedule.persistence;

import net.kravuar.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.id = :scheduleId " +
            "AND s.active = :activeOnly " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    Optional<Schedule> findById(long scheduleId, boolean activeOnly);

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.service.id = :serviceId " +
            "AND s.active = true " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true " +
            "AND ((s.start >= :from AND s.start <= :to) " +
            "OR (s.end >= :from AND s.end <= :to) " +
            "OR (s.start <= :from AND s.end >= :to))")
    List<Schedule> findAllByService(@Param("serviceId") long serviceId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.staff.id = :staffId " +
            "AND s.service.id = :serviceId " +
            "AND s.active = true " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true " +
            "AND ((s.start >= :from AND s.start <= :to) " +
            "OR (s.end >= :from AND s.end <= :to) " +
            "OR (s.start <= :from AND s.end >= :to))")
    List<Schedule> findAllByStaffAndService(@Param("staffId") long staffId, @Param("serviceId") long serviceId, @Param("from") LocalDate from, @Param("to") LocalDate to);
}

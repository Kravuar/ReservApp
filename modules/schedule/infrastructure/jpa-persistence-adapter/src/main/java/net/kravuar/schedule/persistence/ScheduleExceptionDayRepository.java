package net.kravuar.schedule.persistence;

import net.kravuar.schedule.domain.ScheduleExceptionDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
interface ScheduleExceptionDayRepository extends JpaRepository<ScheduleExceptionDay, Long> {
    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.staff.id = :staffId " +
            "AND s.service.id = :serviceId " +
            "AND s.date = :date " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    Optional<ScheduleExceptionDay> findFullyActiveByStaffAndService(long staffId, long serviceId, LocalDate date);
    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.staff.id = :staffId " +
            "AND s.service.id = :serviceId " +
            "AND s.date BETWEEN :from AND :to " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    List<ScheduleExceptionDay> findAllFullyActiveByStaffAndService(long staffId, long serviceId, LocalDate from, LocalDate to);

    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.service.id = :serviceId " +
            "AND s.date BETWEEN :from AND :to " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    List<ScheduleExceptionDay> findAllFullyActiveByService(long serviceId, LocalDate from, LocalDate to);
}

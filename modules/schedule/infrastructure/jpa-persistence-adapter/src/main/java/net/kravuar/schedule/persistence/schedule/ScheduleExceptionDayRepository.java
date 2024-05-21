package net.kravuar.schedule.persistence.schedule;

import net.kravuar.schedule.model.ScheduleExceptionDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
interface ScheduleExceptionDayRepository extends JpaRepository<ScheduleExceptionDay, Long> {
    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.staff.id = :staffId " +
            "AND s.service.id = :serviceId " +
            "AND s.date = :date " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    Optional<ScheduleExceptionDay> findFullyActiveByStaffAndService(@Param("staffId") long staffId, @Param("serviceId") long serviceId, @Param("date") LocalDate date);

    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.staff.id = :staffId " +
            "AND s.service.id = :serviceId " +
            "AND s.date BETWEEN :from AND :to " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    List<ScheduleExceptionDay> findAllFullyActiveByStaffAndService(@Param("staffId") long staffId, @Param("serviceId") long serviceId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.service.id = :serviceId " +
            "AND s.date BETWEEN :from AND :to " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    List<ScheduleExceptionDay> findAllFullyActiveByService(@Param("serviceId") long serviceId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT s FROM ScheduleExceptionDay s " +
            "WHERE s.service.id IN :serviceIds " +
            "AND s.date BETWEEN :from AND :to " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true " +
            "AND s.staff.active = true")
    List<ScheduleExceptionDay> findAllFullyActiveByServices(@Param("serviceIds") Set<Long> serviceIds, @Param("from") LocalDate from, @Param("to") LocalDate to);
}

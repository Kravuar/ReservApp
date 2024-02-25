package net.kravuar.staff.persistence.schedule;

import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ScheduleRepository extends JpaRepository<ScheduleModel, Long> {
    List<ScheduleModel> findAllByServiceAndValidFromAfter(Service service, LocalDate date);
    List<ScheduleModel> findAllByServiceAndStaffAndValidFromAfter(Service service, Staff staff, LocalDate date);
}

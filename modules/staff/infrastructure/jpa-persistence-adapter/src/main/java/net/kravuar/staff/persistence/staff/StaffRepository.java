package net.kravuar.staff.persistence.staff;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findAllByBusiness(Business business);
}

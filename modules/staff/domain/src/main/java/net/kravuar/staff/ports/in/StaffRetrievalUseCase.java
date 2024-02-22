package net.kravuar.staff.ports.in;

import jakarta.validation.Valid;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.commands.StaffAnswerInvitationCommand;
import net.kravuar.staff.domain.commands.StaffInvitationCommand;
import net.kravuar.staff.domain.commands.StaffRemovalCommand;
import net.kravuar.staff.domain.exceptions.*;

import java.util.List;

public interface StaffRetrievalUseCase {
    /**
     * Find staff member by ID.
     *
     * @param id ID of the staff member to find
     * @return {@link Staff} object associated with the provided ID
     * @throws StaffNotFoundException if staff member wasn't found
     */
    Staff findStaffById(long id);

    /**
     * Find all staff members by business.
     *
     * @param businessId ID of the business
     * @return {@link List<Staff>} all staff members associated with the provided business ID
     */
    List<Staff> findAllStaffByBusiness(long businessId);
}
package net.kravuar.staff.ports.out;

import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.model.AccountDetails;

public interface AccountRetrievalPort {
    /**
     * Checks whether accounts exists by sub
     *
     * @param sub subject value of the account to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean exists(String sub);

    /**
     * Retrieves user info by sub
     *
     * @param sub subject value of the account to retrieve
     * @return {@code AccountDetails} with information about account
     * @throws StaffNotFoundException if staff wasn't found
     */
    AccountDetails getBySub(String sub);
}

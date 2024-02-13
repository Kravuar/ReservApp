package net.kravuar.business.ports.out;

import net.kravuar.business.domain.exceptions.AccountNotFoundException;

public interface AccountCheckingPort {
    /**
     * Checks whether account has verified email.
     *
     * @param accountId id of an account to check
     * @throws AccountNotFoundException if account wasn't found
     * @return {@code true} if has, {@code false} otherwise
     */
    boolean hasVerifiedEmail(long accountId);
}

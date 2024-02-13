package net.kravuar.accounts.ports.out;

import net.kravuar.accounts.domain.Account;

public interface CodeGeneratorPort {
    /**
     * Generates verification code based on provided account.
     *
     * @param account to generate code from
     * @return generated code
     */
    String generate(Account account);
    /**
     * Validates verification code based on provided account.
     *
     * @param account to validate code against
     * @return {@code true} if code is valid, {@code false} otherwise
     */
    boolean isValid(String verificationCode, Account account);
}

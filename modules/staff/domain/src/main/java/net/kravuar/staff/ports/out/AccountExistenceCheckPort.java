package net.kravuar.staff.ports.out;

public interface AccountExistenceCheckPort {
    /**
     * Checks whether accounts exists by sub
     *
     * @param sub subject value of the account to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean exists(String sub);
}

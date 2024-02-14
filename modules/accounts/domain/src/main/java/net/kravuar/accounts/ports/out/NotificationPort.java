package net.kravuar.accounts.ports.out;

public interface NotificationPort {
    void onEmailVerifiedChange(long accountId, boolean verified);
    void onAccountCreation(long accountId, String username, String email);
}

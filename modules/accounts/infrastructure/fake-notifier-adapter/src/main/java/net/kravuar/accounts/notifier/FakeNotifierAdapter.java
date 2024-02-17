package net.kravuar.accounts.notifier;

import net.kravuar.accounts.ports.out.NotificationPort;
import net.kravuar.context.AppComponent;

@AppComponent
class FakeNotifierAdapter implements NotificationPort {

    @Override
    public void onEmailVerifiedChange(long accountId, boolean verified) {
        System.out.printf("EMAIL-VERIFIED: %d - %s", accountId, verified);
    }

    @Override
    public void onAccountCreation(long accountId, String username, String email, boolean emailVerified) {
        System.out.printf("EMAIL-CREATED: %d - %s, %s, %s", accountId, username, email, emailVerified);
    }
}

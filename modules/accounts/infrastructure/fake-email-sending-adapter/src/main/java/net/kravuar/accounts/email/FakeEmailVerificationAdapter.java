package net.kravuar.accounts.email;

import net.kravuar.accounts.ports.out.EmailSendingPort;
import net.kravuar.context.AppComponent;

@AppComponent
class FakeEmailVerificationAdapter implements EmailSendingPort {

    @Override
    public void sendEmail(String email, String message) {
        System.out.println("EMAIL: " + email + " | " + message);
    }
}

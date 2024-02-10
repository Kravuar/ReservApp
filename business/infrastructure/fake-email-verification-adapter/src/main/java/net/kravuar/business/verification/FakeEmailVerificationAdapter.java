package net.kravuar.business.verification;

import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.stereotype.Component;

@Component
class FakeEmailVerificationAdapter implements EmailVerificationPort {

    @Override
    public void sendVerificationEmail(String email) {
        // Ignore
        System.out.println("verification " + email);
    }

    @Override
    public boolean verify(String email, String verificationCode) {
        return true;
    }
}

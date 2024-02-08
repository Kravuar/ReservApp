package net.kravuar.business.adapters;

import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.stereotype.Component;

@Component
public class FakeEmailVerificationAdapter implements EmailVerificationPort {

    @Override
    public void sendVerificationMessage(String email) {
        // Ignore
    }

    @Override
    public boolean verify(String email, String verificationCode) {
        //
        return true;
    }
}

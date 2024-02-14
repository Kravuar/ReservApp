package net.kravuar.accounts.password;

import net.kravuar.accounts.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DelegatingPasswordEncoderAdapter implements PasswordEncoderPort {
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}

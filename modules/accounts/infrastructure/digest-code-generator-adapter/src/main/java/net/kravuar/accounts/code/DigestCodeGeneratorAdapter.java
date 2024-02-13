package net.kravuar.accounts.code;

import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.ports.out.CodeGeneratorPort;
import net.kravuar.context.AppComponent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@AppComponent
public class DigestCodeGeneratorAdapter implements CodeGeneratorPort {
    private static final String SALT = UUID.randomUUID().toString();
    private static final MessageDigest ALGORITHM;

    static {
        try {
            ALGORITHM = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generate(Account account) {
        String input = account.getId() + account.getEmail() + SALT;
        return hashString(input);
    }

    @Override
    public boolean isValid(String verificationCode, Account account) {
        String expectedCode = generate(account);
        return expectedCode.equals(verificationCode);
    }

    private static String hashString(String input) {
        byte[] hash = ALGORITHM.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}

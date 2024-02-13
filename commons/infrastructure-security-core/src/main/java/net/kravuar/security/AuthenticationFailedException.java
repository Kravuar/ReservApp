package net.kravuar.security;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}

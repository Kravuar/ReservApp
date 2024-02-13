package net.kravuar.business.domain.exceptions;

public class EmailNotVerifiedException extends RuntimeException {
    public EmailNotVerifiedException() {
        super("Email not verified");
    }
}

package net.kravuar.business.domain.exceptions;

public class BusinessWithEmailAlreadyExistsException extends RuntimeException {
    public BusinessWithEmailAlreadyExistsException(String email) {
        super("Business with such email already exists: " + email);
    }
}

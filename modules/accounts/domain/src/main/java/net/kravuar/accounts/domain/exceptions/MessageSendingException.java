package net.kravuar.accounts.domain.exceptions;

public class MessageSendingException extends Exception {
    public MessageSendingException() {
        super("Could not send message");
    }
}

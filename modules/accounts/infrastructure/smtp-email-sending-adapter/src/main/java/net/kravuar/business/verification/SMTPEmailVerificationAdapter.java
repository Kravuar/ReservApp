package net.kravuar.business.verification;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.exceptions.MessageSendingException;
import net.kravuar.accounts.ports.out.EmailSendingPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
class SMTPEmailVerificationAdapter implements EmailSendingPort {
    private final JavaMailSender mailSender;
    @Value("${email.subject}")
    private final String subject;
    @Value("${email.template}")
    private final String template;

    @Override
    public void sendEmail(String email, String message) throws MessageSendingException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(MessageFormat.format(
                template,
                message
        ));
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            var wrapped = new MessageSendingException();
            wrapped.initCause(e);
            throw wrapped;
        }
    }
}

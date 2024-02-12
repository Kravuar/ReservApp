package net.kravuar.business.verification;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.MessageFormat;

@RequiredArgsConstructor
class SMTPEmailVerificationAdapter implements EmailVerificationPort {
    private final JavaMailSender mailSender;
    @Value("${email.subject}")
    private final String subject;
    @Value("${email.template}")
    private final String template;
    @Value("${email.url-code-param-name}")
    private final String codeParam;

    @Override
    public void sendVerificationCode(String email) throws MessageSendingException {
        SimpleMailMessage verificationMail = new SimpleMailMessage();
        Token token = getCode(email);
        String link = String.format("{}?{}={}", getUrl(), codeParam, token.getValue());
        /* TODO: Named template formatting */
        verificationMail.setText(MessageFormat.format(
                template,
                link,
                token.getExpiration()
        ));
        verificationMail.setTo(email);
        verificationMail.setSubject(subject);

        try {
            mailSender.send(verificationMail);
        } catch (MailException e) {
            var wrapped = new MessageSendingException();
            wrapped.initCause(e);
            throw wrapped;
        }
    }

    @Override
    public boolean verify(String email, String verificationCode) {
        return false;
    }
}

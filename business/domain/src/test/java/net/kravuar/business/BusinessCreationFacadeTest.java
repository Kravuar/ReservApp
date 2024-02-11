package net.kravuar.business;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.BusinessIncorrectEmailVerificationCodeException;
import net.kravuar.business.domain.exceptions.BusinessWithEmailAlreadyExistsException;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BusinessCreationFacadeTest {
    private BusinessPersistencePort businessPersistencePort;
    private EmailVerificationPort emailVerificationPort;
    private BusinessCreationFacade businessCreationFacade;

    @BeforeEach
    void setUp() {
        businessPersistencePort = mock(BusinessPersistencePort.class);
        emailVerificationPort = mock(EmailVerificationPort.class);
        businessCreationFacade = new BusinessCreationFacade(
                businessPersistencePort,
                emailVerificationPort
        );
    }

    @Test
    void givenCorrectCommand_WhenVerifyEmail_ThenVerificationMessageSent() throws MessageSendingException {
        // given
        BusinessEmailVerificationCommand command = new BusinessEmailVerificationCommand("be@bebe.be");
        doNothing().when(emailVerificationPort).sendVerificationCode(anyString());

        // when
        businessCreationFacade.sendVerification(command);

        // then
        verify(emailVerificationPort, times(1)).sendVerificationCode(eq(command.email()));
    }

    @Test
    void givenCorrectCommand_AndEmailIsntUsed_AndCodeCorrect_WhenBusinessCreation_ThenBusinessCreated() {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand(
                "bebe",
                "verynotused@email",
                "very correct code"
        );
        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
        when(businessPersistencePort.existsByEmail(command.email())).thenReturn(false);
        when(emailVerificationPort.verify(anyString(), argThat(command.emailVerificationCode()::equals))).thenReturn(true);

        // when
        Business newBusiness = businessCreationFacade.create(command);

        // then
        verify(businessPersistencePort, times(1)).existsByEmail(eq(command.email()));
        verify(emailVerificationPort, times(1)).verify(eq(command.email()), eq(command.emailVerificationCode()));
        assertEquals(command.email(), newBusiness.getEmail());
        assertEquals(command.name(), newBusiness.getName());
    }

    @Test
    void givenCorrectCommand_ButEmailIsUsed_WhenBusinessCreation_ThenBusinessWithEmailAlreadyExistsException() {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand(
                "bebe",
                "veryused@email",
                "doesn't matter"
        );
        when(businessPersistencePort.existsByEmail(command.email())).thenReturn(true);

        // when & then
        assertThrows(BusinessWithEmailAlreadyExistsException.class, () -> businessCreationFacade.create(command));

        verify(businessPersistencePort, times(1)).existsByEmail(eq(command.email()));
        verify(emailVerificationPort, times(0)).verify(eq(command.email()), eq(command.emailVerificationCode()));
        verify(businessPersistencePort, times(0)).save(any(Business.class));
    }

    @Test
    void givenCorrectCommand_AndEmailIsntUsed_ButCodeIsNotCorrect_WhenBusinessCreation_ThenBusinessIncorrectEmailVerificationCodeException() {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand(
                "bebe",
                "verynotused@email",
                "very bad code"
        );
        when(businessPersistencePort.existsByEmail(command.email())).thenReturn(false);
        when(emailVerificationPort.verify(anyString(), argThat(command.emailVerificationCode()::equals))).thenReturn(false);

        // when & then
        assertThrows(BusinessIncorrectEmailVerificationCodeException.class, () -> businessCreationFacade.create(command));

        verify(businessPersistencePort, times(1)).existsByEmail(eq(command.email()));
        verify(emailVerificationPort, times(1)).verify(eq(command.email()), eq(command.emailVerificationCode()));
        verify(businessPersistencePort, times(0)).save(any(Business.class));
    }

    @Test
    void givenIncorrectCommand_WhenCreateBusiness_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand("3", "` .@be", "");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertEquals(3, validator.validateParameters(
                    businessCreationFacade,
                    BusinessCreationFacade.class.getMethod("create", BusinessCreationCommand.class),
                    new Object[] {command}
            ).size());
        }
    }

    @Test
    void givenIncorrectCommand_WhenVerifyBusinessEmail_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand("3", "not an email", "");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertEquals(3, validator.validateParameters(
                    businessCreationFacade,
                    BusinessCreationFacade.class.getMethod("create", BusinessCreationCommand.class),
                    new Object[] {command}
            ).size());
        }
    }
}
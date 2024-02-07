package net.kravuar.business;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeEmailCommand;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.domain.commands.BusinessCreationCommand;
import net.kravuar.business.domain.commands.BusinessEmailVerificationCommand;
import net.kravuar.business.domain.exceptions.MessageSendingException;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import net.kravuar.business.ports.out.EmailVerificationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BusinessManagementFacadeTest {
    private BusinessPersistencePort businessPersistencePort;
    private EmailVerificationPort emailVerificationPort;
    private BusinessManagementFacade businessManagementFacade;

    @BeforeEach
    void setUp() {
        businessPersistencePort = mock(BusinessPersistencePort.class);
        emailVerificationPort = mock(EmailVerificationPort.class);
        businessManagementFacade = new BusinessManagementFacade(businessPersistencePort, emailVerificationPort);
    }

    @Test
    void givenCorrectCommand_WhenCreateBusiness_ThenNonActiveBusinessCreated() throws MessageSendingException {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand("Bebebusiness", "be@bebe.be");
        doNothing().when(emailVerificationPort).sendVerificationMessage(anyString());
        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));

        // when
        Business createdBusiness = businessManagementFacade.create(command);

        // then
        verify(emailVerificationPort, times(1)).sendVerificationMessage(eq(command.email()));
        verify(businessPersistencePort, times(1)).save(any(Business.class));
        assertEquals(command.name(), createdBusiness.getName());
        assertEquals(command.email(), createdBusiness.getEmail());
        assertFalse(createdBusiness.isEmailVerified());
    }

    @Test
    void givenCorrectCommand_WhenBusinessNameChange_ThenNameChanged() {
        // given
        BusinessChangeNameCommand command = new BusinessChangeNameCommand(1L, "newBebebusiness");
        Business business = mock(Business.class);
        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);

        // when
        businessManagementFacade.changeName(command);

        // then
        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
        verify(business, times(1)).setName(eq(command.newName()));
        verify(businessPersistencePort, times(1)).save(same(business));
    }

    @Test
    void givenCorrectCommand_WhenBusinessEmailChange_ThenEmailChanged() throws MessageSendingException {
        // given
        BusinessChangeEmailCommand command = new BusinessChangeEmailCommand(1L, "newbe@bebe.be");
        Business business = mock(Business.class);
        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);

        // when
        businessManagementFacade.changeEmail(command);

        // then
        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
        verify(business, times(1)).setEmail(eq(command.newEmail()));
        verify(businessPersistencePort, times(1)).save(same(business));
    }

    @Test
    void givenCorrectCommand_WhenBusinessEmailVerification_ThenEmailVerified() {
        // given
        BusinessEmailVerificationCommand command = new BusinessEmailVerificationCommand(1L, "yes, i am very email");
        Business business = mock(Business.class);
        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);
        when(emailVerificationPort.verify(anyString(), argThat(command.verificationCode()::equals))).thenReturn(true);
        when(business.isEmailVerified()).thenReturn(false);
        when(business.getEmail()).thenReturn("email");

        // when
        boolean verified = businessManagementFacade.verifyEmail(command);

        // then
        assertTrue(verified);
        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
        verify(business, times(1)).setEmailVerified(eq(true));
        verify(businessPersistencePort, times(1)).save(same(business));
    }

    @Test
    void givenCorrectCommand_WhenBusinessEmailVerification_AndCodeIsNotCorrect_ThenEmailIsNotVerified() {
        // given
        BusinessEmailVerificationCommand command = new BusinessEmailVerificationCommand(1L, "yes, i am very email *lies*");
        Business business = mock(Business.class);
        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);
        when(emailVerificationPort.verify(anyString(), argThat(command.verificationCode()::equals))).thenReturn(false);
        when(business.isEmailVerified()).thenReturn(false);
        when(business.getEmail()).thenReturn("email");

        // when
        boolean verified = businessManagementFacade.verifyEmail(command);

        // then
        assertFalse(verified);
        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
        verify(business, never()).setEmailVerified(eq(true));
        verify(businessPersistencePort, never()).save(same(business));
    }

    @Test
    void givenIncorrectCommand_WhenCreateBusiness_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessCreationCommand command = new BusinessCreationCommand("", ".@be");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertFalse(validator.validateParameters(
                    businessManagementFacade,
                    BusinessManagementFacade.class.getMethod("create", BusinessCreationCommand.class),
                    new Object[] {command}
            ).isEmpty());
        }
    }

    @Test
    void givenIncorrectCommand_WhenChangeBusinessName_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessChangeNameCommand command = new BusinessChangeNameCommand(1L, "");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertFalse(validator.validateParameters(
                    businessManagementFacade,
                    BusinessManagementFacade.class.getMethod("changeName", BusinessChangeNameCommand.class),
                    new Object[] {command}
            ).isEmpty());
        }
    }

    @Test
    void givenIncorrectCommand_WhenChangeBusinessEmail_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessChangeEmailCommand command = new BusinessChangeEmailCommand(1L, "very not email");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertFalse(validator.validateParameters(
                    businessManagementFacade,
                    BusinessManagementFacade.class.getMethod("changeEmail", BusinessChangeEmailCommand.class),
                    new Object[] {command}
            ).isEmpty());
        }
    }

    @Test
    void givenIncorrectCommand_WhenVerifyBusinessEmail_ThenConstraintViolations() throws NoSuchMethodException {
        // given
        BusinessEmailVerificationCommand command = new BusinessEmailVerificationCommand(1L, "");
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            ExecutableValidator validator = factory.getValidator().forExecutables();

            // when & then
            assertFalse(validator.validateParameters(
                    businessManagementFacade,
                    BusinessManagementFacade.class.getMethod("verifyEmail", BusinessEmailVerificationCommand.class),
                    new Object[] {command}
            ).isEmpty());
        }
    }
}
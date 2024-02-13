package net.kravuar.business;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.commands.BusinessChangeNameCommand;
import net.kravuar.business.ports.out.BusinessPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BusinessManagementFacadeTest {
//    private BusinessPersistencePort businessPersistencePort;
//    private EmailVerificationPort emailVerificationPort;
//    private BusinessManagementFacade businessManagementFacade;
//
//    @BeforeEach
//    void setUp() {
//        businessPersistencePort = mock(BusinessPersistencePort.class);
//        emailVerificationPort = mock(EmailVerificationPort.class);
//        businessManagementFacade = new BusinessManagementFacade(
//                businessPersistencePort,
//                emailVerificationPort
//        );
//    }
//
//    @Test
//    void givenCorrectCommand_WhenBusinessNameChange_ThenNameChanged() {
//        // given
//        BusinessChangeNameCommand command = new BusinessChangeNameCommand(1L, "newBebebusiness");
//        Business business = mock(Business.class);
//        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
//        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);
//
//        // when
//        businessManagementFacade.changeName(command);
//
//        // then
//        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
//        verify(business, times(1)).setName(eq(command.newName()));
//        verify(businessPersistencePort, times(1)).save(same(business));
//    }
//
//    @Test
//    void givenCorrectCommand_AndCorrectCode_WhenBusinessEmailChange_ThenEmailChanged() {
//        // given
//        BusinessChangeEmailCommand command = new BusinessChangeEmailCommand(1L, "newbe@bebe.be", "very correct code");
//        Business business = mock(Business.class);
//        when(businessPersistencePort.save(any(Business.class))).thenAnswer(args -> args.getArgument(0));
//        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);
//        when(emailVerificationPort.verify(command.newEmail(), command.verificationCode())).thenReturn(true);
//
//        // when
//        businessManagementFacade.changeEmail(command);
//
//        // then
//        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
//        verify(emailVerificationPort, times(1)).verify(eq(command.newEmail()), eq(command.verificationCode()));
//        verify(business, times(1)).setEmail(eq(command.newEmail()));
//        verify(businessPersistencePort, times(1)).save(same(business));
//    }
//
//    @Test
//    void givenCorrectCommand_ButIncorrectCode_WhenBusinessEmailChange_ThenBusinessIncorrectEmailVerificationCodeException() {
//        // given
//        BusinessChangeEmailCommand command = new BusinessChangeEmailCommand(1L, "newbe@bebe.be", "very correct code");
//        Business business = mock(Business.class);
//        when(businessPersistencePort.findById(command.businessId())).thenReturn(business);
//        when(emailVerificationPort.verify(command.newEmail(), command.verificationCode())).thenReturn(false);
//
//        // when & then
//        assertThrows(BusinessIncorrectEmailVerificationCodeException.class, () -> businessManagementFacade.changeEmail(command));
//
//        verify(businessPersistencePort, times(1)).findById(eq(command.businessId()));
//        verify(emailVerificationPort, times(1)).verify(eq(command.newEmail()), eq(command.verificationCode()));
//        verify(business, times(0)).setEmail(eq(command.newEmail()));
//        verify(businessPersistencePort, times(0)).save(same(business));
//    }
//
//    @Test
//    void givenIncorrectCommand_WhenChangeBusinessName_ThenConstraintViolations() throws NoSuchMethodException {
//        // given
//        BusinessChangeNameCommand command = new BusinessChangeNameCommand(1L, "");
//        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
//            ExecutableValidator validator = factory.getValidator().forExecutables();
//
//            // when & then
//            assertEquals(1, validator.validateParameters(
//                    businessManagementFacade,
//                    BusinessManagementFacade.class.getMethod("changeName", BusinessChangeNameCommand.class),
//                    new Object[] {command}
//            ).size());
//        }
//    }
//
//    @Test
//    void givenIncorrectCommand_WhenChangeBusinessEmail_ThenConstraintViolations() throws NoSuchMethodException {
//        // given
//        BusinessChangeEmailCommand command = new BusinessChangeEmailCommand(1L, "very not email", "");
//        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
//            ExecutableValidator validator = factory.getValidator().forExecutables();
//
//            // when & then
//            assertEquals(2, validator.validateParameters(
//                    businessManagementFacade,
//                    BusinessManagementFacade.class.getMethod("changeEmail", BusinessChangeEmailCommand.class),
//                    new Object[] {command}
//            ).size());
//        }
//    }
}
package net.kravuar.business.verification;

import net.kravuar.business.ports.out.EmailVerificationPort;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnMissingBean(EmailVerificationPort.class)
@Import(FakeEmailVerificationAdapter.class)
class FakeVerifierAutoConfig {}

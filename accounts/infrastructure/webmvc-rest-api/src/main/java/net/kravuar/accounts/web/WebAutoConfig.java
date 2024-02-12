package net.kravuar.accounts.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(AccountAuthenticationController.class)
class WebAutoConfig {}

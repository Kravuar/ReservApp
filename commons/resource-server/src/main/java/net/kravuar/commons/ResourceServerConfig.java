package net.kravuar.commons;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before = SecurityAutoConfiguration.class)
@Import({WebFlux.class, WebMVC.class})
class ResourceServerConfig {}
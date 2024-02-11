package net.kravuar.business.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({BusinessCreationController.class, BusinessManagementController.class})
public class WebAutoConfig {}

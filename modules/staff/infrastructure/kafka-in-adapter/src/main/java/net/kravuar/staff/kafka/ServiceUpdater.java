package net.kravuar.staff.kafka;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.services.ServiceActivityChangeDTO;
import net.kravuar.integration.services.ServiceCreationDTO;
import net.kravuar.staff.domain.commands.ServiceChangeActiveCommand;
import net.kravuar.staff.domain.commands.ServiceCreationCommand;
import net.kravuar.staff.ports.in.LocalServiceManagementUseCase;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "staffServicesUpdater", topics = "${services.update.topic}")
public class ServiceUpdater {
    private final LocalServiceManagementUseCase localServiceManagementUseCase;

    @KafkaHandler
    void onServiceCreated(ServiceCreationDTO creationDTO) {
        localServiceManagementUseCase.create(new ServiceCreationCommand(
                        creationDTO.id(),
                        creationDTO.businessId(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onServiceCreated(ServiceActivityChangeDTO changeDTO) {
        localServiceManagementUseCase.changeActive(new ServiceChangeActiveCommand(
                changeDTO.id(),
                changeDTO.active()
        ));
    }
}

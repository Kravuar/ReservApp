package net.kravuar.services.kafka;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.integration.business.BusinessCreationDTO;
import net.kravuar.services.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.services.domain.commands.BusinessCreationCommand;
import net.kravuar.services.ports.in.BusinessManagementUseCase;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "businessUpdates", topics = "${business.update.topic}")
public class BusinessUpdater {
    private final BusinessManagementUseCase businessManagementUseCase;

    @KafkaHandler
    void onBusinessCreated(BusinessCreationDTO creationDTO) {
        businessManagementUseCase.create(new BusinessCreationCommand(
                        creationDTO.id(),
                        creationDTO.ownerSub(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onBusinessCreated(BusinessActivityChangeDTO changeDTO) {
        businessManagementUseCase.changeActive(new BusinessChangeActiveCommand(
                changeDTO.id(),
                changeDTO.active()
        ));
    }
}

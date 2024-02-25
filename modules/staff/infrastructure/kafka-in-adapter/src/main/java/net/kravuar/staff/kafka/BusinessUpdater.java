package net.kravuar.staff.kafka;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.integration.business.BusinessCreationDTO;
import net.kravuar.staff.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.staff.domain.commands.BusinessCreationCommand;
import net.kravuar.staff.ports.in.LocalBusinessManagementUseCase;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "staffBusinessUpdater", topics = "${business.update.topic}")
public class BusinessUpdater {
    private final LocalBusinessManagementUseCase localBusinessManagementUseCase;

    @KafkaHandler
    void onBusinessCreated(BusinessCreationDTO creationDTO) {
        localBusinessManagementUseCase.create(new BusinessCreationCommand(
                        creationDTO.id(),
                        creationDTO.ownerSub(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onBusinessCreated(BusinessActivityChangeDTO changeDTO) {
        localBusinessManagementUseCase.changeActive(new BusinessChangeActiveCommand(
                changeDTO.id(),
                changeDTO.active()
        ));
    }
}

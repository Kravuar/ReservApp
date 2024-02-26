package net.kravuar.services.kafka;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.services.domain.commands.HandleBusinessActiveChangeCommand;
import net.kravuar.services.ports.in.HandleBusinessUpdatesUseCase;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "servicesBusinessUpdater", topics = "${business.update.topic}")
public class BusinessUpdatesAdapter {
    private final HandleBusinessUpdatesUseCase handleBusinessUpdatesUseCase;

    @KafkaHandler
    void onBusinessCreated(BusinessActivityChangeDTO changeDTO) {
        handleBusinessUpdatesUseCase.onActivityChange(new HandleBusinessActiveChangeCommand(
                changeDTO.id(),
                changeDTO.active()
        ));
    }
}

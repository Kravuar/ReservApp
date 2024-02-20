package net.kravuar.services.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.commands.BusinessChangeActiveCommand;
import net.kravuar.services.domain.commands.BusinessCreationCommand;
import net.kravuar.services.ports.in.BusinessManagementUseCase;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class BusinessUpdater {
    private final BusinessManagementUseCase businessManagementUseCase;

    @KafkaListener(id = "creationListener", topics = "${services.updates.businessCreationTopic}")
    void onBusinessCreated(BusinessCreationMessage message) {
        businessManagementUseCase.create(new BusinessCreationCommand(
                        message.businessId,
                        message.ownerSub,
                        message.active
                )
        );
    }

    @KafkaListener(id = "creationListener", topics = "${services.updates.businessActivityChangedTopic}")
    void onBusinessCreated(BusinessActivityChangedMessage message) {
        businessManagementUseCase.changeActive(new BusinessChangeActiveCommand(
                message.businessId,
                message.active
        ));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record BusinessCreationMessage(long businessId, String ownerSub, boolean active) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record BusinessActivityChangedMessage(long businessId, boolean active) {
    }
}

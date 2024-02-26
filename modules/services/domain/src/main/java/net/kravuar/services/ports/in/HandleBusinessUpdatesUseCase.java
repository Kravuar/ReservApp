package net.kravuar.services.ports.in;

import net.kravuar.services.domain.Business;
import net.kravuar.services.domain.commands.HandleBusinessActiveChangeCommand;

public interface HandleBusinessUpdatesUseCase {

    /**
     * Handles {@link Business} enabled/disabled events.
     *
     * @param command the command containing information of business activity change
     */
    void onActivityChange(HandleBusinessActiveChangeCommand command);
}
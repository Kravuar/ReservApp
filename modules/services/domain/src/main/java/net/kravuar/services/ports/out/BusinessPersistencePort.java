package net.kravuar.services.ports.out;

import jakarta.validation.Valid;
import net.kravuar.context.AppValidated;
import net.kravuar.services.domain.Business;

@AppValidated
public interface BusinessPersistencePort {
    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     */
    Business save(@Valid Business business);
}

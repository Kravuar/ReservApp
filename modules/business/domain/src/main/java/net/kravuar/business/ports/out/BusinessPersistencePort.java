package net.kravuar.business.ports.out;

import jakarta.validation.Valid;
import net.kravuar.business.domain.Business;
import net.kravuar.business.domain.exceptions.BusinessNameAlreadyTaken;
import net.kravuar.context.AppValidated;

@AppValidated
public interface BusinessPersistencePort {
    /**
     * Save business.
     *
     * @param business business to save
     * @return saved {@link Business} object
     * @throws BusinessNameAlreadyTaken if business name already taken
     */
    Business save(@Valid Business business);
}

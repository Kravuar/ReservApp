package net.kravuar.business.ports.out;

import net.kravuar.business.domain.Business;

public interface BusinessPersistencePort {
    Business findById(long id);
    Business save(Business business);
}

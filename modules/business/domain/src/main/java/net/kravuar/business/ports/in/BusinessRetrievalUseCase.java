package net.kravuar.business.ports.in;

import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.Business;
import net.kravuar.pageable.Page;

public interface BusinessRetrievalUseCase {
    /**
     * Find business by id.
     *
     * @param id         id of the business to find
     * @param activeOnly whether to search active only
     * @return {@link Business} associated the with provided businessId
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long id, boolean activeOnly);

    /**
     * Find businesses by owner sub with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @param sub        sub of the owner
     * @param activeOnly whether to search active only
     * @return page of businesses associated the with provided owner {@code sub}
     */
    Page<Business> findBySub(String sub, boolean activeOnly, int page, int pageSize);

    /**
     * Find active businesses with pageable.
     *
     * @param page page number
     * @param pageSize size of the page
     * @return page of existing active businesses
     */
    Page<Business> findActive(int page, int pageSize);
}
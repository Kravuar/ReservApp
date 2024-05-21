package net.kravuar.business.ports.out;

import net.kravuar.business.domain.exceptions.BusinessNotFoundException;
import net.kravuar.business.model.Business;
import net.kravuar.pageable.Page;

import java.util.List;
import java.util.Set;

public interface BusinessRetrievalPort {
    /**
     * Find business by id.
     *
     * @param businessId id of the business to find
     * @param activeOnly whether to search active only
     * @return {@link Business} associated the with provided {@code businessId}
     * @throws BusinessNotFoundException if business wasn't found
     */
    Business findById(long businessId, boolean activeOnly);

    /**
     * Check whether an active business exists by name.
     *
     * @param name name to check
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean existsActiveByName(String name);

    /**
     * Find businesses by owner with pageable.
     *
     * @param page       page number
     * @param pageSize   size of the page
     * @param sub        sub of the owner
     * @param activeOnly whether to search active only
     * @return page of businesses associated the with provided owner {@code sub}
     */
    Page<Business> findBySub(String sub, boolean activeOnly, int page, int pageSize);

    /**
     * Find active with pageable.
     *
     * @param page     page number
     * @param pageSize size of the page
     * @return page of existing and active businesses
     */
    Page<Business> findActive(int page, int pageSize);

    /**
     * Find businesses by ids.
     *
     * @param ids         ids of the businesses to find
     * @param activeOnly whether to search active only
     * @return list of {@link Business} entities associated the with provided ids
     */
    List<Business> findByIds(Set<Long> ids, boolean activeOnly);
}
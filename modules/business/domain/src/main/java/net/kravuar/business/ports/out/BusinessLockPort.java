package net.kravuar.business.ports.out;

public interface BusinessLockPort {
    /**
     * Lock action on the business id
     *
     * @param businessId id to lock on
     * @param acquire    whether to acquire/release lock
     */
    void lock(long businessId, boolean acquire);

    /**
     * Lock action on the business name
     *
     * @param name    name to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(String name, boolean acquire);
}
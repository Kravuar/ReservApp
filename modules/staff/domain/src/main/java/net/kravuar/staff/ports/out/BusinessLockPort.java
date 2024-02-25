package net.kravuar.staff.ports.out;

public interface BusinessLockPort {
    /**
     * Lock action on the business id
     * @param businessId id to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(long businessId, boolean acquire);
}
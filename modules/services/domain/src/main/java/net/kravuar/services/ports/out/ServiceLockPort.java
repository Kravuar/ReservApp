package net.kravuar.services.ports.out;

public interface ServiceLockPort {
    /**
     * Lock action on the service id
     *
     * @param serviceId id to lock on
     * @param acquire   whether to acquire/release lock
     */
    void lock(long serviceId, boolean acquire);
}
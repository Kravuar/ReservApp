package net.kravuar.services.ports.out;

public interface ServiceLockPort {
    /**
     * Lock action on the service id
     * @param serviceId id to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(long serviceId, boolean acquire);

    /**
     * Lock action on the service name
     * @param name name to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(String name, boolean acquire);
}
package net.kravuar.staff.ports.out;

public interface ScheduleLockPort {
    /**
     * Lock action on the service and staff.
     *
     * @param staffId staff id to lock on
     * @param serviceId service id to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(long serviceId, long staffId, boolean acquire);
}